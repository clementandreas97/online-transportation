
package services.order;

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
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Order3ImplService", targetNamespace = "http://order.services/", wsdlLocation = "http://localhost:8083/services/order3?wsdl")
public class Order3ImplService
    extends Service
{

    private final static URL ORDER3IMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException ORDER3IMPLSERVICE_EXCEPTION;
    private final static QName ORDER3IMPLSERVICE_QNAME = new QName("http://order.services/", "Order3ImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8083/services/order3?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ORDER3IMPLSERVICE_WSDL_LOCATION = url;
        ORDER3IMPLSERVICE_EXCEPTION = e;
    }

    public Order3ImplService() {
        super(__getWsdlLocation(), ORDER3IMPLSERVICE_QNAME);
    }

    public Order3ImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ORDER3IMPLSERVICE_QNAME, features);
    }

    public Order3ImplService(URL wsdlLocation) {
        super(wsdlLocation, ORDER3IMPLSERVICE_QNAME);
    }

    public Order3ImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ORDER3IMPLSERVICE_QNAME, features);
    }

    public Order3ImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Order3ImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Order3
     */
    @WebEndpoint(name = "Order3ImplPort")
    public Order3 getOrder3ImplPort() {
        return super.getPort(new QName("http://order.services/", "Order3ImplPort"), Order3.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Order3
     */
    @WebEndpoint(name = "Order3ImplPort")
    public Order3 getOrder3ImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://order.services/", "Order3ImplPort"), Order3.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ORDER3IMPLSERVICE_EXCEPTION!= null) {
            throw ORDER3IMPLSERVICE_EXCEPTION;
        }
        return ORDER3IMPLSERVICE_WSDL_LOCATION;
    }

}