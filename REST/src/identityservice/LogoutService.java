package identityservice;

import com.google.gson.JsonObject;
import utility.DatabaseConnector;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogoutService() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String token = request.getParameter("token");
        String userId = request.getParameter("user_id");

        PrintWriter out = response.getWriter();

        // create connection to the database
        Connection databaseConnection = DatabaseConnector.connect("blackjek_account");

        try {
            String updateQuery = "update user set access_token = NULL, expiry_time = NULL where user_id = ? and access_token = ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateQuery);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, token);

            preparedStatement.executeUpdate();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", "true");
            out.write(jsonObject.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("success", "false");
            out.write(jsonObject.toString());
        }

        DatabaseConnector.close(databaseConnection);
    }

}
