
package ws.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for creditTransactionTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="creditTransactionTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REFUND"/>
 *     &lt;enumeration value="TOPUP"/>
 *     &lt;enumeration value="USAGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "creditTransactionTypeEnum")
@XmlEnum
public enum CreditTransactionTypeEnum {

    REFUND,
    TOPUP,
    USAGE;

    public String value() {
        return name();
    }

    public static CreditTransactionTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
