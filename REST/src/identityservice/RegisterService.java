package identityservice;

import com.google.gson.JsonObject;
import utility.DatabaseConnector;
import utility.TokenGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterService extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // get the POST response parameter
        String userAgent = request.getParameter("user-agent");
        String ipAddress = request.getParameter("ip");
        String name = request.getParameter("name");
        String username = request.getParameter("uname");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String phone = request.getParameter("phnum");
        boolean isDriver = request.getParameter("isdriver").equals("true");



        // create connection to the database
        Connection databaseConnection = DatabaseConnector.connect("blackjek_account");
        PreparedStatement preparedStatement;

        // create connection to the database_service
//        Connection databaseConnection1 = DatabaseConnector.connect("blackjek_service");
//        PreparedStatement preparedStatement1;

        try {
            String expiryTime = new TokenGenerator().generateExpiryTime();
            String token = new TokenGenerator().generateToken(username ,expiryTime, userAgent, ipAddress);

            String command = "insert into user (uname, email, password, access_token, expiry_time) VALUES(?,?,?,?,?)";
            preparedStatement = databaseConnection.prepareStatement(command);
            //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, token);
            preparedStatement.setString(5, expiryTime);

            preparedStatement.executeUpdate();

            String selectQuery = "select user_id from user where uname = ?";
            preparedStatement = databaseConnection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();

            String userId = "-1";
            if (result.next()) {
                userId = result.getString("user_id");
            }

//            String command1 = "insert into user (name, email, phone, profile_picture, is_driver) VALUES(?,?,?,?,?)";
//            preparedStatement1 = databaseConnection1.prepareStatement(command1); //Making use of prepared statements here to insert bunch of data
//            preparedStatement1.setString(1, name);
//            preparedStatement1.setString(2, email);
//            preparedStatement1.setString(3, phone);
//            preparedStatement1.setString(4, "");
//            preparedStatement1.setBoolean(5, isDriver);
//            preparedStatement1.executeUpdate();


            // TODO :add token

            JsonObject json = new JsonObject();
            json.addProperty("token", token);
            json.addProperty("id", userId);
            json.addProperty("is_driver", isDriver ? "true" : "false");
            PrintWriter out = response.getWriter();
            out.write(json.toString());

            result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnector.close(databaseConnection);

    }

}
