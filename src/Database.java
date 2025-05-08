import java.sql.*;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/hospitalmanagment";
    private static final String USER = "root";
    private static final String PASS = "root@123";

    public static Connection getConnection() throws SQLException {
        try {
            // Manually load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
