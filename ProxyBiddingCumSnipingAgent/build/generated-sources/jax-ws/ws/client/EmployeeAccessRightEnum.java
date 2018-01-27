
package ws.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for employeeAccessRightEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="employeeAccessRightEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FINANCE"/>
 *     &lt;enumeration value="SALES"/>
 *     &lt;enumeration value="SYSADMIN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "employeeAccessRightEnum")
@XmlEnum
public enum EmployeeAccessRightEnum {

    FINANCE,
    SALES,
    SYSADMIN;

    public String value() {
        return name();
    }

    public static EmployeeAccessRightEnum fromValue(String v) {
        return valueOf(v);
    }

}
