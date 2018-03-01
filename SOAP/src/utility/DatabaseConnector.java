package utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    public static Connection connect(String dbname) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // TODO :should read from config file later
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean close(Connection connection) {
        try {
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
