package services.profile;

import services.validator.TokenValidator;
import services.validator.TokenValidatorImpl;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utility.DatabaseConnector;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.PreparedStatement;

//Service Implementation
@WebService(endpointInterface = "services.profile.EditProfile")
public class EditProfileImpl implements EditProfile {
    @Override
    public String editProfile(String token, String username, String userid, String name, String phone, boolean isDriver, String picture) {
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
                BASE64Decoder base64Decoder = new BASE64Decoder();
                byte[] pictureDecoded = base64Decoder.decodeBuffer(picture);
                if (picture.equals("")) {
                    // should not delete the picture
                    String command = "UPDATE user SET name = ?, phone = ?, is_driver = ? WHERE user_id = ? ";
                    preparedStatement = databaseConnection.prepareStatement(command);
                    //Making use of prepared statements here to insert bunch of data
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, phone);
                    preparedStatement.setBoolean(3, isDriver);
                    preparedStatement.setString(4, userid);
                } else {
                    String command = "UPDATE user SET name = ?, phone = ?, is_driver = ?, profile_picture = ? WHERE user_id = ? ";
                    preparedStatement = databaseConnection.prepareStatement(command);
                    //Making use of prepared statements here to insert bunch of data
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, phone);
                    preparedStatement.setBoolean(3, isDriver);
                    preparedStatement.setBytes(4, pictureDecoded);
                    preparedStatement.setString(5, userid);
                }

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


