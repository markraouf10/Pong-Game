package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {   //rabtet el database
    private static final String URL = "jdbc:mysql://localhost:3306/pong_game";
    private static final String USER = "root";
    private static final String PASSWORD = "markraouf"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
