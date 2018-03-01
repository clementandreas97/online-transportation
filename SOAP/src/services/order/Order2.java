package services.order;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Order2 {

  @WebMethod
  String getDriverDest (String token, String username, String destination, String userid);
  @WebMethod
  String getDriverPref (String token, String username, String driverName, String userid);

}
