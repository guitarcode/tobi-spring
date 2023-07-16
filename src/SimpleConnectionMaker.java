import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    public Connection makeConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/tobi", "root", "root");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
