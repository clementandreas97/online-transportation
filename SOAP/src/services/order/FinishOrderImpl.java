package services.order;

import services.validator.TokenValidatorImpl;
import utility.DatabaseConnector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebService(endpointInterface = "services.order.FinishOrder")
public class FinishOrderImpl implements FinishOrder {
    @Override
    public String insert_data_order(String token, String username, String user_id, String driver_id, String picking_point, String destination, String rating, String comment) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            int rowAffected = 0;

            // create connection to the database
            Connection databaseConnection = DatabaseConnector.connect("blackjek_service");
            PreparedStatement preparedStatement;

            try {

                // Generate the SQL query.
                String command = "INSERT INTO orders (user_id, driver_id, picking_point, destination, rating, comment, date, is_hide_user, is_hide_driver) VALUES (?,?,?,?,?,?,DATE(CURDATE()),?,?)";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, user_id);
                preparedStatement.setString(2, driver_id);
                preparedStatement.setString(3, picking_point);
                preparedStatement.setString(4, destination);
                preparedStatement.setString(5, rating);
                preparedStatement.setString(6, comment);
                preparedStatement.setString(7, "0");
                preparedStatement.setString(8, "0");

                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected > 0) {
                    // successful query execution
                    return "status=valid";
                } else {
                    // failure at query execution
                    return "status=valid&success=fail";
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                // Close the connection.
                try {
                    DatabaseConnector.close(databaseConnection);
                } catch (Exception ec) {
                    ec.printStackTrace();
                }
            }
        } else {
            // status invalid or expired
            return String.format("status=%s", status);
        }

        // undefined error
        return "status=error";
    }

    @Override
    public String insert_data_user(String token, String username, String driver_id, String rating) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            int rowAffected = 0;

            // create connection to the database
            Connection databaseConnection = DatabaseConnector.connect("blackjek_service");
            PreparedStatement preparedStatement;

            try {

                // Generate the SQL query.
                String command = "UPDATE user SET rating = (((rating* votes)+?)/(votes + 1)), votes = (votes + 1) WHERE user_id = ? ";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setFloat(1, Float.parseFloat(rating));
                preparedStatement.setString(2, driver_id);

                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected > 0) {
                    // successful query execution
                    return "status=valid";
                } else {
                    // failure at query execution
                    return "status=valid&success=fail";
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                // Close the connection.
                try {
                    DatabaseConnector.close(databaseConnection);
                } catch (Exception ec) {
                    ec.printStackTrace();
                }
            }
        } else {
            // status invalid or expired
            return String.format("status=%s", status);
        }

        // undefined error
        return "status=error";
    }
}
