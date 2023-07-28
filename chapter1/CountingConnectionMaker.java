import java.sql.Connection;

public class CountingConnectionMaker implements ConnectionMaker{
    private ConnectionMaker realConnectionMaker;
    private int connectionCount;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.connectionCount = 0;
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() {
        connectionCount++;
        return realConnectionMaker.makeConnection();
    }

    public int getConnectionCount() {
        return this.connectionCount;
    }
}
