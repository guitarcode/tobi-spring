import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySqlConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/tobi", "root", "root");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
