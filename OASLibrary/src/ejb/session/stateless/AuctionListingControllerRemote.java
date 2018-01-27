/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import Entity.Address;
import Entity.AuctionListing;
import java.util.List;
import util.exception.AuctionListingNotFoundException;

/**
 *
 * @author josh
 */
public interface AuctionListingControllerRemote {

    public AuctionListing createAuctionListing(AuctionListing auctionListing);

    public AuctionListing retrieveAuctionListingByID(Long auctionListingID) throws AuctionListingNotFoundException;

    public List<AuctionListing> retrieveAllAuctionListings() throws AuctionListingNotFoundException;

    public List<AuctionListing> retrieveAuctionListingsBelowReservePrice() throws AuctionListingNotFoundException;

    public List<AuctionListing> retrieveActiveAuctionListings() throws AuctionListingNotFoundException;

    public List<AuctionListing> retrieveWonAuctionListings(Long customerID);

    public void updateAuctionListing(AuctionListing auctionListing);

    public void deleteAuctionListing(Long auctionListingID);

    public void setWinningBid(Long auctionListingID);

    public void noWinningBid(Long auctionListingID);

    public void openAuctionListing(Long auctionListingID);
    
    public void closeAuctionListing(Long auctionListingID);

    public void removeDeliveryAddress(Address address);

}
