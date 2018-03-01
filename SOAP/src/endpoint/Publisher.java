package endpoint;

import javax.xml.ws.Endpoint;

import services.history.HistoryImpl;
import services.order.FinishOrderImpl;
import services.profile.EditProfileImpl;
import services.profile.FetchProfileImpl;
import services.register.RegisterUserImpl;
import services.validator.TokenValidatorImpl;
import services.location.LocationImpl;
import services.order.Order2Impl;
import services.order.Order3Impl;

//Endpoint publisher
public class Publisher{

    public static void main(String[] args) {



        Endpoint.publish("http://localhost:8083/services/validatetoken", new TokenValidatorImpl());
        Endpoint.publish("http://localhost:8083/services/gethistory", new HistoryImpl());
        Endpoint.publish("http://localhost:8083/services/order2", new Order2Impl());
        Endpoint.publish("http://localhost:8083/services/order3", new Order3Impl());
        Endpoint.publish("http://localhost:8083/services/finishorder", new FinishOrderImpl());
        Endpoint.publish("http://localhost:8083/services/profile/fetchprofile", new FetchProfileImpl());
        Endpoint.publish("http://localhost:8083/services/profile/editprofile", new EditProfileImpl());
        Endpoint.publish("http://localhost:8083/services/location/editlocation", new LocationImpl());
        Endpoint.publish("http://localhost:8083/services/register/registeruser", new RegisterUserImpl());
    }

}