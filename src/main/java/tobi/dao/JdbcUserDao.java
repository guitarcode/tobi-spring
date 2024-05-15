package tobi.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tobi.domain.Level;
import tobi.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> mapper = (rs, rowNum) -> {
        return new User(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("password"),
                Level.valueOf(rs.getInt("level")),
                rs.getInt("login"),
                rs.getInt("recommend")
        );
    };

    public JdbcUserDao() {
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user) {
        jdbcTemplate.update("insert into users(id, username, password, level, login, recommend) values (?,?,?,?,?,?)",
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getLevel().getValue(),
                user.getLogin(),
                user.getRecommend()
                );
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[] {id},
                mapper
        );
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(
                "select * from users",
                mapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) as userCount from users",
                Integer.class
        );
    }
}
