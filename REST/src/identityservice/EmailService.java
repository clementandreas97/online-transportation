package identityservice;

import com.google.gson.JsonObject;
import utility.DatabaseConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailService extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // get the username to be checked from GET method
        String email = request.getParameter("email");

        // create connection to the database
        Connection databaseConnection = DatabaseConnector.connect("blackjek_account");

        try {
            String selectQuery = "select * from user where email = ?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);

            ResultSet result = preparedStatement.executeQuery();
            JsonObject jsonObject = new JsonObject();

            if (result.next()) {
                // email has been used
                jsonObject.addProperty("check", false);
            } else {
                // email has not been used
                jsonObject.addProperty("check", true);
            }
            response.getWriter().println(jsonObject.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseConnector.close(databaseConnection);
    }
}
