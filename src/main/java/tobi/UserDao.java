package tobi;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private DataSource dataSource;

    public UserDao() {
    }

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConnectionMaker(DataSource connectionMaker) {
        this.dataSource = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy strategy) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = strategy.makeStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    public void add(User user) {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into users(id, username, password) values (?,?,?)");

                ps.setString(1, user.getId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
    }

    public User getById(String id) throws SQLException {


        Connection connection = dataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                "select * from users where id = ?");

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();

        User user = null;

        if (resultSet.next()) {
            user = new User();

            user.setId(resultSet.getString("id"));
            user.setUserName(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        } else {
            throw new EmptyResultDataAccess();
        }

        closeConnection(ps, connection);
        return user;
    }

    public void deleteAll() {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(
                        "delete from users"
                );
            }
        });
    }

    public long getCount() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select count(*) as userCount from users"
        );

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        long userCount = resultSet.getLong("userCount");

        closeConnection(preparedStatement, connection);

        return userCount;
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
