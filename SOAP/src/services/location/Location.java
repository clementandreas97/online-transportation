package services.location;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Location {
    @WebMethod
    String deleteLocation(String token, String username, String userId, String location);

    String editLocation(String token, String username, String userId, String newLocation, String oldLocation);

    String addLocation(String token, String username, String userId, String location);

}
