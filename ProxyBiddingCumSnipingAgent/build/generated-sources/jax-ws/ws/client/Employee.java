
package ws.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for employee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="employee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accessRight" type="{http://ws.session.ejb/}employeeAccessRightEnum" minOccurs="0"/>
 *         &lt;element name="auctionListing" type="{http://ws.session.ejb/}auctionListing" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="creditPackages" type="{http://ws.session.ejb/}creditPackage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="employeeID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", propOrder = {
    "accessRight",
    "auctionListing",
    "creditPackages",
    "employeeID",
    "lastName",
    "password",
    "username"
})
public class Employee {

    protected EmployeeAccessRightEnum accessRight;
    @XmlElement(nillable = true)
    protected List<AuctionListing> auctionListing;
    @XmlElement(nillable = true)
    protected List<CreditPackage> creditPackages;
    protected Long employeeID;
    protected String lastName;
    protected String password;
    protected String username;

    /**
     * Gets the value of the accessRight property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeAccessRightEnum }
     *     
     */
    public EmployeeAccessRightEnum getAccessRight() {
        return accessRight;
    }

    /**
     * Sets the value of the accessRight property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeAccessRightEnum }
     *     
     */
    public void setAccessRight(EmployeeAccessRightEnum value) {
        this.accessRight = value;
    }

    /**
     * Gets the value of the auctionListing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the auctionListing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuctionListing().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuctionListing }
     * 
     * 
     */
    public List<AuctionListing> getAuctionListing() {
        if (auctionListing == null) {
            auctionListing = new ArrayList<AuctionListing>();
        }
        return this.auctionListing;
    }

    /**
     * Gets the value of the creditPackages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creditPackages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreditPackages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreditPackage }
     * 
     * 
     */
    public List<CreditPackage> getCreditPackages() {
        if (creditPackages == null) {
            creditPackages = new ArrayList<CreditPackage>();
        }
        return this.creditPackages;
    }

    /**
     * Gets the value of the employeeID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEmployeeID() {
        return employeeID;
    }

    /**
     * Sets the value of the employeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEmployeeID(Long value) {
        this.employeeID = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

}
