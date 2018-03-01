package services.history;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface History {
    @WebMethod
    String getPrevOrder(String token, String username, int id);
    @WebMethod
    String getDriverHistory(String token, String username, int id);
    @WebMethod
    String hideHistory(String token, String username, int id, String role);
}
