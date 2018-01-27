
package ws.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creditTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="creditTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creditPackage" type="{http://ws.session.ejb/}creditPackage" minOccurs="0"/>
 *         &lt;element name="creditTransactionID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="customer" type="{http://ws.session.ejb/}customer" minOccurs="0"/>
 *         &lt;element name="transactionAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="transactionType" type="{http://ws.session.ejb/}creditTransactionTypeEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creditTransaction", propOrder = {
    "creditPackage",
    "creditTransactionID",
    "customer",
    "transactionAmt",
    "transactionType"
})
public class CreditTransaction {

    protected CreditPackage creditPackage;
    protected Long creditTransactionID;
    protected Customer customer;
    protected BigDecimal transactionAmt;
    protected CreditTransactionTypeEnum transactionType;

    /**
     * Gets the value of the creditPackage property.
     * 
     * @return
     *     possible object is
     *     {@link CreditPackage }
     *     
     */
    public CreditPackage getCreditPackage() {
        return creditPackage;
    }

    /**
     * Sets the value of the creditPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditPackage }
     *     
     */
    public void setCreditPackage(CreditPackage value) {
        this.creditPackage = value;
    }

    /**
     * Gets the value of the creditTransactionID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreditTransactionID() {
        return creditTransactionID;
    }

    /**
     * Sets the value of the creditTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreditTransactionID(Long value) {
        this.creditTransactionID = value;
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
     * Gets the value of the transactionAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTransactionAmt() {
        return transactionAmt;
    }

    /**
     * Sets the value of the transactionAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTransactionAmt(BigDecimal value) {
        this.transactionAmt = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link CreditTransactionTypeEnum }
     *     
     */
    public CreditTransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditTransactionTypeEnum }
     *     
     */
    public void setTransactionType(CreditTransactionTypeEnum value) {
        this.transactionType = value;
    }

}
