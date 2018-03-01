package identityservice;

import com.google.gson.JsonObject;
import utility.DatabaseConnector;
import utility.TokenGenerator;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginService() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String username = request.getParameter("uname");
        String password = request.getParameter("pass");
        String userAgent = request.getParameter("user-agent");
        String ipAddress = request.getParameter("ip");

        // create connection to the database
        Connection databaseConnection = DatabaseConnector.connect("blackjek_account");

        try {
            String selectQuery = "select * from user where uname = ? and password = ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();
            PrintWriter out = response.getWriter();

            if (result.next()) {
                String expiryTime = new TokenGenerator().generateExpiryTime();
                String userId = result.getString("user_id");
                String token = new TokenGenerator().generateToken(username, expiryTime, userAgent, ipAddress);

                // insert token and expiry time to database
                String insertQuery = "update user set access_token = ?, expiry_time = ? where user_id = ?";
                PreparedStatement preparedStatement1 = databaseConnection.prepareStatement(insertQuery);
                preparedStatement1.setString(1, token);
                preparedStatement1.setString(2, expiryTime);
                preparedStatement1.setString(3, userId);

                preparedStatement1.executeUpdate();

                // send the token and user id in json format
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("token", token);
                jsonObject.addProperty("id", userId);
                out.write(jsonObject.toString());
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("error", "loginfailed");
                out.write(jsonObject.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnector.close(databaseConnection);
    }

}
