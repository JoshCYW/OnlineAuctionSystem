/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import util.enumeration.CreditTransactionTypeEnum;

/**
 *
 * @author josh
 */
@Entity
public class CreditTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditTransactionID;
    @Column(precision = 18, scale = 4, nullable = false)
    private BigDecimal transactionAmt;
    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private CreditTransactionTypeEnum transactionType;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(nullable = true)
    private CreditPackage creditPackage;

    public CreditTransaction() {
    }

    public CreditTransaction(BigDecimal transactionAmt, CreditTransactionTypeEnum transactionType) {
        this.transactionAmt = transactionAmt;
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmt() {
        return transactionAmt;
    }

    public CreditTransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CreditPackage getCreditPackage() {
        return creditPackage;
    }
    
    
    public Long getCreditTransactionID() {
        return creditTransactionID;
    }

    public void setCreditTransactionID(Long creditTransactionID) {
        this.creditTransactionID = creditTransactionID;
    }

    public void setTransactionAmt(BigDecimal transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public void setTransactionType(CreditTransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCreditPackage(CreditPackage creditPackage) {
        this.creditPackage = creditPackage;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditTransactionID != null ? creditTransactionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CreditTransaction)) {
            return false;
        }
        CreditTransaction other = (CreditTransaction) object;
        if ((this.creditTransactionID == null && other.creditTransactionID != null) || (this.creditTransactionID != null && !this.creditTransactionID.equals(other.creditTransactionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CreditTransaction[ id=" + creditTransactionID + " ]";
    }
    
}
