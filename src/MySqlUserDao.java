import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlUserDao extends UserDao{
    @Override
    protected Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/tobi", "root", "root");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
