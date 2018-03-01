package services.register;
import utility.DatabaseConnector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebService(endpointInterface = "services.register.RegisterUser")
public class RegisterUserImpl implements RegisterUser{
  @Override
  public boolean insert_to_user(String name, String email, String phone, String profile_picture, String is_driver) {
    // create connection to the database_service
    Connection databaseConnection1 = DatabaseConnector.connect("blackjek_service");
    PreparedStatement preparedStatement1;
    int rowAffected = 0;

    try {


      String command1 = "insert into user (name, email, phone, profile_picture, is_driver) VALUES(?,?,?,?,?)";
      preparedStatement1 = databaseConnection1.prepareStatement(command1); //Making use of prepared statements here to insert bunch of data
      preparedStatement1.setString(1, name);
      preparedStatement1.setString(2, email);
      preparedStatement1.setString(3, phone);
      preparedStatement1.setString(4, "");
      preparedStatement1.setString(5, is_driver);
      preparedStatement1.executeUpdate();


      rowAffected = preparedStatement1.executeUpdate();
      if (rowAffected > 0) {
        // successful query execution
        return true;
      } else {
        // failure at query execution
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    DatabaseConnector.close(databaseConnection1);
    return false;
  }
}
