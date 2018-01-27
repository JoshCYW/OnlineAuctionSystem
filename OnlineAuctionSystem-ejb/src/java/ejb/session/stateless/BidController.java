/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.AuctionListing;
import Entity.Bid;
import Entity.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InsufficientCreditsException;
import util.exception.InvalidBidException;

/**
 *
 * @author josh
 */
@Stateless
@Local(BidControllerLocal.class)
@Remote(BidControllerRemote.class)
public class BidController implements BidControllerRemote, BidControllerLocal {

    @PersistenceContext(unitName = "OnlineAuctionSystem-ejbPU")
    private EntityManager em;

    @EJB
    private customerControllerLocal customerControllerLocal;
    @EJB
    private AuctionListingControllerLocal auctionListingControllerLocal;
    @Resource
    private EJBContext eJBContext;

    @Override
    public BigDecimal getBidIncrement(BigDecimal currentPrice) {
        if (currentPrice.compareTo(BigDecimal.valueOf(0.01)) == 1) {
            return BigDecimal.valueOf(0.05);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(1)) == 1) {
            return BigDecimal.valueOf(0.25);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(5)) == 1) {
            return BigDecimal.valueOf(0.50);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(25)) == 1) {
            return BigDecimal.valueOf(1.00);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(100)) == 1) {
            return BigDecimal.valueOf(2.50);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(250)) == 1) {
            return BigDecimal.valueOf(5);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(500)) == 1) {
            return BigDecimal.valueOf(10);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(1000)) == 1) {
            return BigDecimal.valueOf(25);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(2500)) == 1) {
            return BigDecimal.valueOf(50);
        } else if (currentPrice.compareTo(BigDecimal.valueOf(5000)) == 1) {
            return BigDecimal.valueOf(100);
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    public void placeBid(Bid bid) throws InvalidBidException {
        AuctionListing auctionListing = em.find(AuctionListing.class, bid.getAuctionListing().getAuctionListingID());
        Customer customer = em.find(Customer.class, bid.getCustomer().getCustomerID());
        try {
            if (bid.getBidAmt().compareTo(auctionListing.getCurrentBid()) > 0) {
                em.persist(bid);
                auctionListing.getBids().add(bid);
                auctionListing.setCurrentBid(bid.getBidAmt());
            } else {
                throw new InvalidBidException("Bid is lower than current bid!");
            }
            customerControllerLocal.spendCredits(customer.getCustomerID(), bid.getBidAmt());
            List<Bid> bidEntities = auctionListing.getBids();
            if (bidEntities.size() > 1) {
                Bid refundBid = bidEntities.get(bidEntities.size() - 2);
                customerControllerLocal.refundCredits(refundBid.getCustomer().getCustomerID(), refundBid.getBidAmt());
            }

        } catch (InsufficientCreditsException ex) {
            eJBContext.setRollbackOnly();
            throw new InvalidBidException(ex.getMessage());
        }
    }
}
