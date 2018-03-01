package identityservice;

import com.google.gson.JsonObject;
import utility.DatabaseConnector;
import utility.TokenGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TokenRenewer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String token = request.getParameter("token");
        String newExpiryTime = new TokenGenerator().generateExpiryTime();

        // create connection to the database
        Connection databaseConnection = DatabaseConnector.connect("blackjek_account");
        PreparedStatement preparedStatement;

        JsonObject jsonObject = new JsonObject();

        try {
            String updateQuery = "UPDATE user SET expiry_time = ? WHERE access_token = ?";
            preparedStatement = databaseConnection.prepareStatement(updateQuery);
            preparedStatement.setString(1, newExpiryTime);
            preparedStatement.setString(2, token);
            preparedStatement.executeUpdate();

            jsonObject.addProperty("success", "true");
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.addProperty("success", "false");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
