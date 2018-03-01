package services.order;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FinishOrder {

  @WebMethod
  String insert_data_order(String token, String username, String user_id, String driver_id, String picking_point, String destination, String rating, String comment);
  String insert_data_user(String token, String username, String driver_id, String rating);
}
