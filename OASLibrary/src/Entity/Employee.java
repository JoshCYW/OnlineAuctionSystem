/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.enumeration.EmployeeAccessRightEnum;

/**
 *
 * @author josh
 */
@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;
    @Column(length = 64, nullable = false)
    private String firstname;
    @Column(length = 64, nullable = false)
    private String lastName;
    @Column(nullable = false, length = 255, unique = true)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeAccessRightEnum accessRight;
    @OneToMany(mappedBy = "employee")
    private List<AuctionListing> auctionListing;
    @OneToMany(mappedBy = "employee")
    private List<CreditPackage> creditPackages;

    public Employee() {
        auctionListing = new ArrayList<>();
        creditPackages = new ArrayList<>();
    }

    public Employee(String firstname, String lastName, String username, String password, EmployeeAccessRightEnum accessRight) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accessRight = accessRight;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeAccessRightEnum getAccessRight() {
        return accessRight;
    }

    public List<AuctionListing> getAuctionListing() {
        return auctionListing;
    }

    public List<CreditPackage> getCreditPackages() {
        return creditPackages;
    }
    
    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessRight(EmployeeAccessRightEnum accessRight) {
        this.accessRight = accessRight;
    }

    public void setAuctionListing(List<AuctionListing> auctionListing) {
        this.auctionListing = auctionListing;
    }

    public void setCreditPackages(List<CreditPackage> creditPackages) {
        this.creditPackages = creditPackages;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeID != null ? employeeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeID == null && other.employeeID != null) || (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Employee[ id=" + employeeID + " ]";
    }
    
}
