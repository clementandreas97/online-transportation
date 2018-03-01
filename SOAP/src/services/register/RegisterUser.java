package services.register;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RegisterUser {
  @WebMethod
  boolean insert_to_user (String name, String email, String phone, String profile_picture, String is_driver);

}
