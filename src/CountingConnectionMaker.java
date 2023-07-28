import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{
    private final DataSource dataSource;
    private int connectionCount;

    public CountingConnectionMaker(DataSource dataSource) {
        this.connectionCount = 0;
        this.dataSource = dataSource;
    }

    @Override
    public Connection makeConnection() {
        connectionCount++;
        try {
            return dataSource.getConnection();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public int getConnectionCount() {
        return this.connectionCount;
    }
}
