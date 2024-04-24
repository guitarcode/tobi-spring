package tobi;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> mapper = (rs, rowNum) -> {
        return new User(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("password")
        );
    };

    public UserDao() {
    }

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        jdbcTemplate.update("insert into users(id, username, password) values (?,?,?)",
                user.getId(),
                user.getUsername(),
                user.getPassword());
    }

    public User getById(String id) throws SQLException {
        List<User> users = jdbcTemplate.query(
                "select * from users where id = ?",
                mapper,
                id
        );
        return users.get(0);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
        return jdbcTemplate.queryForObject(
                "select count(*) as userCount from users",
                Integer.class
        );
    }
}
