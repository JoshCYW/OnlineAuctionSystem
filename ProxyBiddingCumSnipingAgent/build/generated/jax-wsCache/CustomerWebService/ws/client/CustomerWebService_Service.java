
package ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CustomerWebService", targetNamespace = "http://ws.session.ejb/", wsdlLocation = "http://localhost:8080/CustomerWebService/CustomerWebService?wsdl")
public class CustomerWebService_Service
    extends Service
{

    private final static URL CUSTOMERWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException CUSTOMERWEBSERVICE_EXCEPTION;
    private final static QName CUSTOMERWEBSERVICE_QNAME = new QName("http://ws.session.ejb/", "CustomerWebService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/CustomerWebService/CustomerWebService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CUSTOMERWEBSERVICE_WSDL_LOCATION = url;
        CUSTOMERWEBSERVICE_EXCEPTION = e;
    }

    public CustomerWebService_Service() {
        super(__getWsdlLocation(), CUSTOMERWEBSERVICE_QNAME);
    }

    public CustomerWebService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), CUSTOMERWEBSERVICE_QNAME, features);
    }

    public CustomerWebService_Service(URL wsdlLocation) {
        super(wsdlLocation, CUSTOMERWEBSERVICE_QNAME);
    }

    public CustomerWebService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CUSTOMERWEBSERVICE_QNAME, features);
    }

    public CustomerWebService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CustomerWebService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CustomerWebService
     */
    @WebEndpoint(name = "CustomerWebServicePort")
    public CustomerWebService getCustomerWebServicePort() {
        return super.getPort(new QName("http://ws.session.ejb/", "CustomerWebServicePort"), CustomerWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CustomerWebService
     */
    @WebEndpoint(name = "CustomerWebServicePort")
    public CustomerWebService getCustomerWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.session.ejb/", "CustomerWebServicePort"), CustomerWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CUSTOMERWEBSERVICE_EXCEPTION!= null) {
            throw CUSTOMERWEBSERVICE_EXCEPTION;
        }
        return CUSTOMERWEBSERVICE_WSDL_LOCATION;
    }

}
