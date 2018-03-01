package services.location;

import services.validator.TokenValidatorImpl;
import utility.DatabaseConnector;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;


//Service Implementation
@WebService(endpointInterface = "services.location.Location")
public class LocationImpl implements Location {

    @Override
    public String deleteLocation(String token, String username, String userId, String location) {
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
                String command = "DELETE FROM preferred_location WHERE user_id = ? AND location = ?";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, location);

                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected > 0) {
                    // successful query execution
                    return "status=valid";
                } else {
                    // failure at query execution
                    return "status=valid&success=false";
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
    public String editLocation(String token, String username, String userid, String newLocation, String oldLocation) {
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
                String command = "UPDATE preferred_location SET location = ? WHERE user_id = ? AND location = ? ";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, newLocation);
                preparedStatement.setString(2, userid);
                preparedStatement.setString(3, oldLocation);

                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected > 0) {
                    // successful query execution
                    return "status=valid";
                } else {
                    // failure at query execution
                    return "status=valid&success=false";
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
    public String addLocation(String token, String username, String userId, String location) {
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
                String command = "insert preferred_location values(?, ?) ";
                preparedStatement = databaseConnection.prepareStatement(command);
                //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, location);

                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected > 0) {
                    // successful query execution
                    return "status=valid";
                } else {
                    // failure at query execution
                    return "status=valid&success=false";
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






