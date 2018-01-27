/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import Entity.AuctionListing;
import Entity.Bid;
import Entity.CreditTransaction;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.CreditTransactionTypeEnum;
import util.exception.AuctionListingNotFoundException;

/**
 *
 * @author josh
 */
@Stateless
@Local(AuctionListingControllerLocal.class)
@Remote(AuctionListingControllerRemote.class)
public class AuctionListingController implements AuctionListingControllerRemote, AuctionListingControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @EJB
    private customerControllerLocal customerController;
    @EJB
    private CreditTransactionControllerLocal creditTransactionController;
    @EJB
    private TimerControllerLocal timerController;

    @Override
    public AuctionListing createAuctionListing(AuctionListing auctionListing) {
        em.persist(auctionListing);
        em.flush();
        em.refresh(auctionListing);
        timerController.startTimer(auctionListing.getAuctionListingID(), auctionListing.getStartDate(), "openListing");
        timerController.startTimer(auctionListing.getAuctionListingID(), auctionListing.getEndDate(), "closeListing");
        return auctionListing;
    }

    @Override
    public AuctionListing retrieveAuctionListingByID(Long auctionListingID) throws AuctionListingNotFoundException {
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingID);

        if (auctionListing != null) {
            return auctionListing;
        } else {
            throw new AuctionListingNotFoundException("Auction ID: " + auctionListingID + " does not exist");
        }
    }

    @Override
    public List<AuctionListing> retrieveAllAuctionListings() throws AuctionListingNotFoundException {
        Query query = em.createQuery("SELECT c FROM AuctionListing c");
        List<AuctionListing> auctionListing = query.getResultList();
        return auctionListing;
    }

    @Override
    public List<AuctionListing> retrieveAuctionListingsBelowReservePrice() throws AuctionListingNotFoundException {
        Query query = em.createQuery("SELECT c FROM AuctionListing c WHERE c.manualAssign = true");
        List<AuctionListing> auctionListing = query.getResultList();
        if (auctionListing.isEmpty()) {
            throw new AuctionListingNotFoundException("No auction listings below its reserve price");
        }
        return auctionListing;
    }

    @Override
    public List<AuctionListing> retrieveActiveAuctionListings() throws AuctionListingNotFoundException {
        Query query = em.createQuery("SELECT c FROM AuctionListing c WHERE c.openListing = true");
        List<AuctionListing> auctionListing = query.getResultList();
        if (auctionListing.isEmpty()) {
            throw new AuctionListingNotFoundException("No active auction listings");
        }
        return auctionListing;
    }

    @Override
    public List<AuctionListing> retrieveWonAuctionListings(Long customerID) {
        Query query = em.createQuery("SELECT c FROM AuctionListing c WHERE c.winningBid.customer.customerID = :inCustomerId");
        query.setParameter("inCustomerId", customerID);
        return query.getResultList();
    }

    @Override
    public void updateAuctionListing(AuctionListing auctionListing) {
        auctionListing = em.merge(auctionListing);
        timerController.updateTimer(auctionListing.getAuctionListingID(), auctionListing.getStartDate(), "openListing");
        timerController.updateTimer(auctionListing.getAuctionListingID(), auctionListing.getEndDate(), "closeListing");
    }

    @Override
    public void deleteAuctionListing(Long auctionListingID) {
        BigDecimal refunds;
        Long customerID;
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingID);
        List<Bid> bids = auctionListing.getBids();
        for (Bid bidEntity : bids) {
            refunds = bidEntity.getBidAmt();
            customerID = bidEntity.getCustomer().getCustomerID();
            customerController.refundCredits(customerID, refunds);
            creditTransactionController.createCreditTransaction(new CreditTransaction(refunds, CreditTransactionTypeEnum.REFUND));
        }
        auctionListing.setEnabled(Boolean.FALSE);
        auctionListing.setOpenListing(Boolean.FALSE);
        em.remove(auctionListing);
        em.flush();
    }

    @Override
    public void setWinningBid(Long auctionListingID) {
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingID);
        Bid winningBid = auctionListing.getBids().get(auctionListing.getBids().size() - 1);
        auctionListing.setWinningBid(winningBid);
        auctionListing.setManualAssign(Boolean.FALSE);
        em.persist(auctionListing);
        em.flush();
        em.refresh(auctionListing);
    }

    @Override
    public void noWinningBid(Long auctionListingID) {
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingID);
        auctionListing.setManualAssign(Boolean.FALSE);
        em.persist(auctionListing);
        em.flush();
        em.refresh(auctionListing);
    }

    @Override
    public void openAuctionListing(Long auctionListingID) {
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingID);
        auctionListing.setOpenListing(Boolean.TRUE);
    }

    @Override
    public void closeAuctionListing(Long auctionListingID) {
        AuctionListing auctionListing = em.find(AuctionListing.class, auctionListingID);
        List<Bid> bids = auctionListing.getBids();
        auctionListing.setOpenListing(Boolean.FALSE);
        if (!bids.isEmpty()) {
            BigDecimal reservePrice = auctionListing.getReservePrice();
            Bid bid = bids.get(bids.size() - 1);
            BigDecimal lastOffer = bid.getBidAmt();
            if (reservePrice.compareTo(lastOffer) < 0 || reservePrice == null) {
                auctionListing.setWinningBid(bid);
                bid.setWinningBid(Boolean.TRUE);
                CreditTransaction creditTransaction = new CreditTransaction(lastOffer, CreditTransactionTypeEnum.USAGE);
                creditTransaction.setCustomer(bid.getCustomer());
                creditTransactionController.createCreditTransaction(creditTransaction);
                customerController.deductCreditBalance(bid.getCustomer().getCustomerID(), lastOffer);
            } else if (reservePrice.compareTo(lastOffer) >= 0) {
                auctionListing.setManualAssign(Boolean.TRUE);
            }
        }
    }

    @Override
    public void removeDeliveryAddress(Address address) {
        Query query = em.createQuery("SELECT c FROM AuctionListing c WHERE c.deliveryAddress = ?1");
        query.setParameter(1, address);
        List<AuctionListing> toBeRemoved = query.getResultList();
        for (AuctionListing auctionListing : toBeRemoved) {
            auctionListing.setDeliveryAddress(null);
        }
    }

}
