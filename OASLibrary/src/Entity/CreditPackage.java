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
public class CreditPackage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditPackageID;
    @Column (length = 255, nullable = false, unique = true) 
    private String creditPackageName;
    @Column (precision = 64, scale = 4, nullable = false)        
    private BigDecimal price;
    @Column (precision = 64, scale = 4, nullable = false)
    private BigDecimal numOfCredits;
    @Column(nullable = false)
    private Boolean enabled;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Employee employee;
    @OneToMany(mappedBy = "creditPackage")
    private List<CreditTransaction> creditTransaction;
    

    public CreditPackage() {
        creditTransaction = new ArrayList<>();
    }

    public CreditPackage(String creditPackageName, BigDecimal price, BigDecimal numOfCredits, Boolean enabled) {
        this.creditPackageName = creditPackageName;
        this.price = price;
        this.numOfCredits = numOfCredits;
        this.enabled = enabled;
    }

    public String getCreditPackageName() {
        return creditPackageName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getNumOfCredits() {
        return numOfCredits;
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<CreditTransaction> getCreditTransaction() {
        return creditTransaction;
    }
    
    
    public Long getCreditPackageID() {
        return creditPackageID;
    }

    public void setCreditPackageID(Long creditPackageID) {
        this.creditPackageID = creditPackageID;
    }

    public void setCreditPackageName(String creditPackageName) {
        this.creditPackageName = creditPackageName;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setNumOfCredits(BigDecimal numOfCredits) {
        this.numOfCredits = numOfCredits;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setCreditTransaction(List<CreditTransaction> creditTransaction) {
        this.creditTransaction = creditTransaction;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditPackageID != null ? creditPackageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditPackage)) {
            return false;
        }
        CreditPackage other = (CreditPackage) object;
        if ((this.creditPackageID == null && other.creditPackageID != null) || (this.creditPackageID != null && !this.creditPackageID.equals(other.creditPackageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CreditPackage[ id=" + creditPackageID + " ]";
    }
    
}
