/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author josh
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;
    @Column(length = 64, nullable = false)
    private String firstname;
    @Column(length = 64, nullable = false)
    private String lastName;
    @Column(length = 255, nullable = false, unique = true)
    private String identificationNo;
    @Column(length = 10, nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false, length = 255, unique = true)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(precision = 18, scale = 4)
    private BigDecimal creditBalance;
    @Column(precision = 18, scale = 4)
    private BigDecimal holdingBalance;
    @Column(precision = 18, scale = 4)
    private BigDecimal availableBalance;
    @Column(nullable = false)
    private Boolean premium;
    @OneToMany
    @JoinColumn(nullable = true)
    private List<Address> addresses;
    @OneToMany(mappedBy = "customer")
    private List<CreditTransaction> creditTransactions;
    @OneToMany(mappedBy = "customer")
    private List<Bid> bids;      

    public Customer() {
        addresses = new ArrayList<>();
        creditTransactions = new ArrayList<>();
        bids = new ArrayList<>();
    }

    public Customer(String firstname, String lastName, String identificationNo, String phoneNumber, String username, String password, BigDecimal creditBalance, BigDecimal holdingBalance, BigDecimal availableBalance, Boolean premium) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.identificationNo = identificationNo;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.creditBalance = creditBalance;
        this.holdingBalance = holdingBalance;
        this.availableBalance = availableBalance;
        this.premium = premium;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public BigDecimal getHoldingBalance() {
        return holdingBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public Boolean getPremium() {
        return premium;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<CreditTransaction> getCreditTransactions() {
        return creditTransactions;
    }

    public List<Bid> getBids() {
        return bids;
    }
    
    
    
    
    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public void setHoldingBalance(BigDecimal holdingBalance) {
        this.holdingBalance = holdingBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setCreditTransactions(List<CreditTransaction> creditTransactions) {
        this.creditTransactions = creditTransactions;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerID != null ? customerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerID == null && other.customerID != null) || (this.customerID != null && !this.customerID.equals(other.customerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Customer[ id=" + customerID + " ]";
    }
    
}
