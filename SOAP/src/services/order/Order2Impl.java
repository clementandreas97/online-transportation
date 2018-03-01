package services.order;

import services.validator.TokenValidatorImpl;
import utility.DatabaseConnector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

//Service Implementation
@WebService(endpointInterface = "services.order.Order2")
public class Order2Impl implements Order2 {
    @Override
    public String getDriverPref(String token, String username, String driverName, String userid) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            // create connection to the database
            Connection databaseConnection = DatabaseConnector.connect("blackjek_service");
            PreparedStatement preparedStatement;
            StringBuilder result = new StringBuilder();
            result.append(String.format("status=%s&", status));
            boolean hasName = false;
            try {
                // Generate the SQL query.
                String command = "SELECT * FROM user WHERE LOWER(name) LIKE LOWER(?) AND (is_driver = ?) AND ? <> ? AND user.user_id  != ?";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, "%" + driverName + "%");
                preparedStatement.setInt(2, 1);
                preparedStatement.setString(3, driverName);
                preparedStatement.setString(4, "");
                preparedStatement.setString(5, userid);

                ResultSet rs = preparedStatement.executeQuery();

                if (rs.wasNull()) {
                    return "";
                } else {
                    while (rs.next()) {
                        hasName = true;
                        String name = rs.getString("name");
                        String rating = rs.getString("rating");
                        String votes = rs.getString("votes");
                        String user_id = rs.getString("user_id");
                        String add = String.format("user_id=%s&name=%s&rating=%s&votes=%s\n-\n", user_id, name, rating, votes);

                        result.append(add);
                    }
                    if (!hasName)
                        return result.substring(0, result.length() - 1);
                    return result.substring(0, result.length() - 3);

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
    public String getDriverDest(String token, String username, String destination, String userid) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            Connection databaseConnection = DatabaseConnector.connect("blackjek_service");
            PreparedStatement preparedStatement;
            StringBuilder result = new StringBuilder();
            result.append(String.format("status=%s&", status));
            boolean hasName = false;

            try {

                // Generate the SQL query.
                String command = "SELECT DISTINCT * FROM preferred_location NATURAL JOIN user  WHERE preferred_location.location = ? AND user.is_driver = ? AND user.user_id  != ? ";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, destination);
                preparedStatement.setInt(2, 1);
                preparedStatement.setString(3, userid);

                ResultSet rs = preparedStatement.executeQuery();

                if (rs.wasNull()) {
                    return "";
                } else {
                    while (rs.next()) {
                        hasName = true;
                        String name = rs.getString("name");
                        String rating = rs.getString("rating");
                        String votes = rs.getString("votes");
                        String userId = rs.getString("user_id");
                        String add = String.format("user_id=%s&name=%s&rating=%s&votes=%s\n-\n", userId, name, rating, votes);

                        result.append(add);
                    }
                    if (!hasName)
                        return result.substring(0, result.length() - 1);
                    return result.substring(0, result.length() - 3);

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

        return "status=error";
    }
}
