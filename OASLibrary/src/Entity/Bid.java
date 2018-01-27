/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author josh
 */
@Entity
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidID;
    @Column(precision = 18, scale = 4,nullable = false)
    private BigDecimal bidAmt;
    @Temporal (TemporalType.TIMESTAMP)
    private Calendar bidTime;
    @Column(nullable = true)
    private Boolean winningBid;   
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AuctionListing auctionListing;

    public Bid() {
    }

    public Bid(BigDecimal bidAmt, Calendar bidTime) {
        this.bidAmt = bidAmt;
        this.bidTime = bidTime;
    }

    public BigDecimal getBidAmt() {
        return bidAmt;
    }

    public Calendar getBidTime() {
        return bidTime;
    }

    public Boolean getWinningBid() {
        return winningBid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AuctionListing getAuctionListing() {
        return auctionListing;
    }
    

    public Long getBidID() {
        return bidID;
    }

    public void setBidID(Long bidID) {
        this.bidID = bidID;
    }

    public void setBidAmt(BigDecimal bidAmt) {
        this.bidAmt = bidAmt;
    }

    public void setBidTime(Calendar bidTime) {
        this.bidTime = bidTime;
    }

    public void setWinningBid(Boolean winningBid) {
        this.winningBid = winningBid;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAuctionListing(AuctionListing auctionListing) {
        this.auctionListing = auctionListing;
    }

  
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidID != null ? bidID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.bidID == null && other.bidID != null) || (this.bidID != null && !this.bidID.equals(other.bidID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Bid[ id=" + bidID + " ]";
    }
    
}
