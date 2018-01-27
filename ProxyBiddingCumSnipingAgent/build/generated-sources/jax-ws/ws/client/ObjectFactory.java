
package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RegsterPremiumCustomer_QNAME = new QName("http://ws.session.ejb/", "regsterPremiumCustomer");
    private final static QName _RegsterPremiumCustomerResponse_QNAME = new QName("http://ws.session.ejb/", "regsterPremiumCustomerResponse");
    private final static QName _CustomerLoginResponse_QNAME = new QName("http://ws.session.ejb/", "customerLoginResponse");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginCredentialException");
    private final static QName _CustomerLogin_QNAME = new QName("http://ws.session.ejb/", "customerLogin");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link CustomerLogin }
     * 
     */
    public CustomerLogin createCustomerLogin() {
        return new CustomerLogin();
    }

    /**
     * Create an instance of {@link RegsterPremiumCustomer }
     * 
     */
    public RegsterPremiumCustomer createRegsterPremiumCustomer() {
        return new RegsterPremiumCustomer();
    }

    /**
     * Create an instance of {@link RegsterPremiumCustomerResponse }
     * 
     */
    public RegsterPremiumCustomerResponse createRegsterPremiumCustomerResponse() {
        return new RegsterPremiumCustomerResponse();
    }

    /**
     * Create an instance of {@link CustomerLoginResponse }
     * 
     */
    public CustomerLoginResponse createCustomerLoginResponse() {
        return new CustomerLoginResponse();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link CreditPackage }
     * 
     */
    public CreditPackage createCreditPackage() {
        return new CreditPackage();
    }

    /**
     * Create an instance of {@link CreditTransaction }
     * 
     */
    public CreditTransaction createCreditTransaction() {
        return new CreditTransaction();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link AuctionListing }
     * 
     */
    public AuctionListing createAuctionListing() {
        return new AuctionListing();
    }

    /**
     * Create an instance of {@link Bid }
     * 
     */
    public Bid createBid() {
        return new Bid();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegsterPremiumCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "regsterPremiumCustomer")
    public JAXBElement<RegsterPremiumCustomer> createRegsterPremiumCustomer(RegsterPremiumCustomer value) {
        return new JAXBElement<RegsterPremiumCustomer>(_RegsterPremiumCustomer_QNAME, RegsterPremiumCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegsterPremiumCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "regsterPremiumCustomerResponse")
    public JAXBElement<RegsterPremiumCustomerResponse> createRegsterPremiumCustomerResponse(RegsterPremiumCustomerResponse value) {
        return new JAXBElement<RegsterPremiumCustomerResponse>(_RegsterPremiumCustomerResponse_QNAME, RegsterPremiumCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "customerLoginResponse")
    public JAXBElement<CustomerLoginResponse> createCustomerLoginResponse(CustomerLoginResponse value) {
        return new JAXBElement<CustomerLoginResponse>(_CustomerLoginResponse_QNAME, CustomerLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "customerLogin")
    public JAXBElement<CustomerLogin> createCustomerLogin(CustomerLogin value) {
        return new JAXBElement<CustomerLogin>(_CustomerLogin_QNAME, CustomerLogin.class, null, value);
    }

}
