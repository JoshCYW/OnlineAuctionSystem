
package ws.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for bid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auctionListing" type="{http://ws.session.ejb/}auctionListing" minOccurs="0"/>
 *         &lt;element name="bidAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="bidID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="bidTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://ws.session.ejb/}customer" minOccurs="0"/>
 *         &lt;element name="winningBid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bid", propOrder = {
    "auctionListing",
    "bidAmt",
    "bidID",
    "bidTime",
    "customer",
    "winningBid"
})
public class Bid {

    protected AuctionListing auctionListing;
    protected BigDecimal bidAmt;
    protected Long bidID;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bidTime;
    protected Customer customer;
    protected Boolean winningBid;

    /**
     * Gets the value of the auctionListing property.
     * 
     * @return
     *     possible object is
     *     {@link AuctionListing }
     *     
     */
    public AuctionListing getAuctionListing() {
        return auctionListing;
    }

    /**
     * Sets the value of the auctionListing property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuctionListing }
     *     
     */
    public void setAuctionListing(AuctionListing value) {
        this.auctionListing = value;
    }

    /**
     * Gets the value of the bidAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBidAmt() {
        return bidAmt;
    }

    /**
     * Sets the value of the bidAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBidAmt(BigDecimal value) {
        this.bidAmt = value;
    }

    /**
     * Gets the value of the bidID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBidID() {
        return bidID;
    }

    /**
     * Sets the value of the bidID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBidID(Long value) {
        this.bidID = value;
    }

    /**
     * Gets the value of the bidTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBidTime() {
        return bidTime;
    }

    /**
     * Sets the value of the bidTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBidTime(XMLGregorianCalendar value) {
        this.bidTime = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomer(Customer value) {
        this.customer = value;
    }

    /**
     * Gets the value of the winningBid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWinningBid() {
        return winningBid;
    }

    /**
     * Sets the value of the winningBid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWinningBid(Boolean value) {
        this.winningBid = value;
    }

}
