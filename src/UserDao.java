import java.sql.*;

public class UserDao {
    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException {
        Connection connection = connectionMaker.makeConnection();

        PreparedStatement ps = connection.prepareStatement(
                "insert into users(id, username, password) values (?,?,?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        closeConnection(ps, connection);
    }

    public User getById(String id) throws SQLException {


        Connection connection = connectionMaker.makeConnection();

        PreparedStatement ps = connection.prepareStatement(
                "select * from users where id = ?");

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        closeConnection(ps, connection);
        return user;
    }

    public void deleteAll() throws SQLException {

        Connection connection = connectionMaker.makeConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from users"
        );

        preparedStatement.executeUpdate();

        closeConnection(preparedStatement, connection);
    }

    private void closeConnection(PreparedStatement preparedStatement, Connection connection) {
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
