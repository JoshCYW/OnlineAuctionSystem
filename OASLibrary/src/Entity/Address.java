/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author josh
 */
@Entity
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressID;
    @Column(length = 255, nullable = false)
    private String streetAddress;
    @Column(length = 16, nullable = false)
    private String unitNumber;
    @Column(length = 16, nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private Boolean status;
    @OneToMany(mappedBy = "deliveryAddress")
    private List<AuctionListing> auctionList;

    public Address() {
        auctionList = new ArrayList<>();
    }

    public Address(String streetAddress, String unitNumber, String postalCode, Boolean status) {
        this.streetAddress = streetAddress;
        this.unitNumber = unitNumber;
        this.postalCode = postalCode;
        this.status = status;
    }

 

    
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setAuctionList(List<AuctionListing> auctionList) {
        this.auctionList = auctionList;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Boolean getStatus() {
        return status;
    }


    public List<AuctionListing> getAuctionList() {
        return auctionList;
    }
    
    public Long getAddressID() {
        return addressID;
    }

    public void setAddressID(Long addressID) {
        this.addressID = addressID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressID != null ? addressID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressID == null && other.addressID != null) || (this.addressID != null && !this.addressID.equals(other.addressID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Address[ id=" + addressID + " ]";
    }
    
}
