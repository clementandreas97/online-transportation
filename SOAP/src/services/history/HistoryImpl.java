package services.history;

import services.validator.TokenValidatorImpl;
import sun.misc.BASE64Encoder;
import utility.DatabaseConnector;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebService(endpointInterface = "services.history.History")
public class HistoryImpl implements History {
    @Override
    public String getPrevOrder(String token, String username, int id) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            Connection connection = DatabaseConnector.connect("blackjek_service");
            StringBuilder result = new StringBuilder();
            try {
                String query = "SELECT * FROM user join orders on user.user_id = orders.driver_id WHERE is_hide_user = 0 AND orders.user_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                int result_id = 1;
                result.append(String.format("status=%s&", status));
                boolean hasHistory = false;
                while (resultSet.next()) {
                    hasHistory = true;
                    String date = resultSet.getString("date");
                    String name = resultSet.getString("name");
                    String pickingPoint = resultSet.getString("picking_point");
                    String destination = resultSet.getString("destination");
                    String rating = resultSet.getString("orders.rating");
                    String comment = resultSet.getString("comment");
                    String orderId = resultSet.getString("order_id");
                    byte[] profilePictureBlob = resultSet.getBytes("profile_picture");
                    String profilePicture = new BASE64Encoder().encode(profilePictureBlob);
                    String add = String.format("id=%s&date=%s&name=%s&picking_point=%s&destination=%s&rating=%s&comment=%s" +
                                    "&order_id=%s&profile_picture=%s\n-\n",
                            result_id, date, name, pickingPoint, destination, rating, comment, orderId, profilePicture);
                    result.append(add);
                    result_id++;
                }
                if (!hasHistory)
                    return result.substring(0, result.length() - 1);
                return result.substring(0, result.length() - 3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result.toString();
        } else {
            // status invalid or expired
            return String.format("status=%s", status);
        }
    }

    @Override
    public String hideHistory(String token, String username, int id, String role) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            Connection connection = DatabaseConnector.connect("blackjek_service");
            try {
                String query;
                if (role.equals("driver")) {
                    query = "UPDATE orders SET is_hide_driver = 1 WHERE order_id = ?";
                } else {
                    query = "UPDATE orders SET is_hide_user = 1 WHERE order_id = ?";
                }
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, Integer.toString(id));
                preparedStatement.executeUpdate();
                return "status=valid";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // status invalid or expired
            return String.format("status=%s", status);
        }
        return "status=error";
    }

    @Override
    public String getDriverHistory(String token, String username, int id) {
        TokenValidatorImpl tokenValidator = new TokenValidatorImpl();
        String status = tokenValidator.validateToken(token, username);
        if (status.equals("valid")) {
            // token valid
            Connection connection = DatabaseConnector.connect("blackjek_service");

            StringBuilder result = new StringBuilder();
            try {
                String query = "SELECT * FROM orders JOIN user ON user.user_id = orders.user_id WHERE driver_id = ? AND is_hide_driver = 0";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                int result_id = 1;
                result.append(String.format("status=%s&", status));
                boolean hasHistory = false;
                while (resultSet.next()) {
                    hasHistory = true;
                    String date = resultSet.getString("date");
                    String name = resultSet.getString("name");
                    String pickingPoint = resultSet.getString("picking_point");
                    String destination = resultSet.getString("destination");
                    String rating = resultSet.getString("orders.rating");
                    String comment = resultSet.getString("comment");
                    String orderId = resultSet.getString("order_id");
                    byte[] profilePictureBlob = resultSet.getBytes("profile_picture");
                    String profilePicture = new BASE64Encoder().encode(profilePictureBlob);
                    String add = String.format("id=%s&date=%s&name=%s&picking_point=%s&destination=%s&rating=%s&comment=%s" +
                                    "&order_id=%s&profile_picture=%s\n-\n",
                            result_id, date, name, pickingPoint, destination, rating, comment, orderId, profilePicture);
                    result.append(add);
                    result_id++;
                }
                if (!hasHistory)
                    return result.substring(0, result.length() - 1);
                return result.substring(0, result.length() - 3);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return result.toString();
        } else {
            // status invalid or expired
            return String.format("status=%s", status);
        }
    }
}
