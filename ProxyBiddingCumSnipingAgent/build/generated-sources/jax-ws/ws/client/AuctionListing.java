
package ws.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for auctionListing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auctionListing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auctionListingID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="bids" type="{http://ws.session.ejb/}bid" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="currentBid" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deliveryAddress" type="{http://ws.session.ejb/}address" minOccurs="0"/>
 *         &lt;element name="employee" type="{http://ws.session.ejb/}employee" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="manualAssign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="openListing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reservePrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startingBid" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="winningBid" type="{http://ws.session.ejb/}bid" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auctionListing", propOrder = {
    "auctionListingID",
    "bids",
    "currentBid",
    "deliveryAddress",
    "employee",
    "enabled",
    "endDate",
    "manualAssign",
    "openListing",
    "productName",
    "reservePrice",
    "startDate",
    "startingBid",
    "winningBid"
})
public class AuctionListing {

    protected Long auctionListingID;
    @XmlElement(nillable = true)
    protected List<Bid> bids;
    protected BigDecimal currentBid;
    protected Address deliveryAddress;
    protected Employee employee;
    protected Boolean enabled;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected Boolean manualAssign;
    protected Boolean openListing;
    protected String productName;
    protected BigDecimal reservePrice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    protected BigDecimal startingBid;
    protected Bid winningBid;

    /**
     * Gets the value of the auctionListingID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAuctionListingID() {
        return auctionListingID;
    }

    /**
     * Sets the value of the auctionListingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAuctionListingID(Long value) {
        this.auctionListingID = value;
    }

    /**
     * Gets the value of the bids property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bids property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBids().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bid }
     * 
     * 
     */
    public List<Bid> getBids() {
        if (bids == null) {
            bids = new ArrayList<Bid>();
        }
        return this.bids;
    }

    /**
     * Gets the value of the currentBid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    /**
     * Sets the value of the currentBid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurrentBid(BigDecimal value) {
        this.currentBid = value;
    }

    /**
     * Gets the value of the deliveryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the value of the deliveryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setDeliveryAddress(Address value) {
        this.deliveryAddress = value;
    }

    /**
     * Gets the value of the employee property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the value of the employee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setEmployee(Employee value) {
        this.employee = value;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the manualAssign property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isManualAssign() {
        return manualAssign;
    }

    /**
     * Sets the value of the manualAssign property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setManualAssign(Boolean value) {
        this.manualAssign = value;
    }

    /**
     * Gets the value of the openListing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOpenListing() {
        return openListing;
    }

    /**
     * Sets the value of the openListing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOpenListing(Boolean value) {
        this.openListing = value;
    }

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the reservePrice property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    /**
     * Sets the value of the reservePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReservePrice(BigDecimal value) {
        this.reservePrice = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the startingBid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStartingBid() {
        return startingBid;
    }

    /**
     * Sets the value of the startingBid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStartingBid(BigDecimal value) {
        this.startingBid = value;
    }

    /**
     * Gets the value of the winningBid property.
     * 
     * @return
     *     possible object is
     *     {@link Bid }
     *     
     */
    public Bid getWinningBid() {
        return winningBid;
    }

    /**
     * Sets the value of the winningBid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bid }
     *     
     */
    public void setWinningBid(Bid value) {
        this.winningBid = value;
    }

}
