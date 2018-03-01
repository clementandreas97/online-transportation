package services.order;

import services.validator.TokenValidatorImpl;
import utility.DatabaseConnector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Service Implementation
@WebService(endpointInterface = "services.order.Order3")
public class Order3Impl implements Order3 {
    @Override
    public String getDriverData_Service(String token, String username, String driver_id) {
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
                String command = "SELECT * FROM user WHERE user_id = ? AND (is_driver = ?)";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, driver_id);
                preparedStatement.setInt(2, 1);

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
    public String getDriverData_Account(String token, String username, String driver_id) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            Connection databaseConnection = DatabaseConnector.connect("blackjek_account");
            PreparedStatement preparedStatement;
            StringBuilder result = new StringBuilder();
            result.append(String.format("status=%s&", status));
            boolean hasName = false;

            try {
                // Generate the SQL query.
                String command = "SELECT * FROM user WHERE user_id = ?";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, driver_id);


                ResultSet rs = preparedStatement.executeQuery();

                if (rs.wasNull()) {
                    return "";
                } else {
                    while (rs.next()) {
                        hasName = true;
                        String uname = rs.getString("uname");
                        String user_id = rs.getString("user_id");
                        String add = String.format("user_id=%s&uname=%s\n-\n", user_id, uname);
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
}
