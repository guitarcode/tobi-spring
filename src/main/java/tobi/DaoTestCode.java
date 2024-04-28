package tobi;

import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

public class DaoTestCode {
    public static void baseDaoTestCode(ApplicationContext ac) throws SQLException {
        JdbcUserDao jdbcUserDao = ac.getBean("userDao", JdbcUserDao.class);

        jdbcUserDao.deleteAll();

        User newUser = new User();
        newUser.setId("semin");
        newUser.setUsername("최세민");
        newUser.setPassword("semin");

        jdbcUserDao.add(newUser);

        User getUser = jdbcUserDao.get("semin");
        System.out.println("getUser.getUserName() = " + getUser.getUsername());
    }
}
