/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author josh
 */
@Entity
public class AuctionListing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionListingID;
    @Column(length = 255, nullable = false, unique = true)
    private String productName;
    @Column(precision = 18, scale = 4,nullable = true)
    private BigDecimal startingBid;
    @Column(precision = 18, scale = 4,nullable = true)
    private BigDecimal currentBid;
    @Column(nullable = false)
    private BigDecimal reservePrice;
    @Column(nullable = false)
    private Boolean openListing;
    @Column(nullable = false)
    private Boolean enabled;
    @Column(nullable = false)
    private Boolean manualAssign;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startDate;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endDate;    
    @ManyToOne
    @JoinColumn(nullable = true)
    private Address deliveryAddress;
    @OneToOne(optional = true)
    @JoinColumn(nullable = true)
    private Bid winningBid;
    @OneToMany(mappedBy = "auctionListing")
    private List<Bid> bids;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Employee employee;

    public AuctionListing() {
        bids = new ArrayList<>();
    }

    public AuctionListing(String productName, BigDecimal startingBid, BigDecimal currentBid, BigDecimal reservePrice, Boolean openListing, Boolean enabled, Boolean manualAssign, Calendar startDate, Calendar endDate) {
        this.productName = productName;
        this.startingBid = startingBid;
        this.currentBid = currentBid;
        this.reservePrice = reservePrice;
        this.openListing = openListing;
        this.enabled = enabled;
        this.manualAssign = manualAssign;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getStartingBid() {
        return startingBid;
    }

    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public Boolean getOpenListing() {
        return openListing;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getManualAssign() {
        return manualAssign;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Bid getWinningBid() {
        return winningBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public Employee getEmployee() {
        return employee;
    }
    
    
    
    
    public Long getAuctionListingID() {
        return auctionListingID;
    }

    public void setAuctionListingID(Long auctionListingID) {
        this.auctionListingID = auctionListingID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStartingBid(BigDecimal startingBid) {
        this.startingBid = startingBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    public void setOpenListing(Boolean openListing) {
        this.openListing = openListing;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setManualAssign(Boolean manualAssign) {
        this.manualAssign = manualAssign;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auctionListingID != null ? auctionListingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionListing)) {
            return false;
        }
        AuctionListing other = (AuctionListing) object;
        if ((this.auctionListingID == null && other.auctionListingID != null) || (this.auctionListingID != null && !this.auctionListingID.equals(other.auctionListingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AuctionListing[ id=" + auctionListingID + " ]";
    }
    
}
