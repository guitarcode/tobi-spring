package tobi;

import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

public class DaoTestCode {
    public static void baseDaoTestCode(ApplicationContext ac) throws SQLException {
        UserDao userDao = ac.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        User newUser = new User();
        newUser.setId("semin");
        newUser.setUserName("최세민");
        newUser.setPassword("semin");

        userDao.add(newUser);

        User getUser = userDao.getById("semin");
        System.out.println("getUser.getUserName() = " + getUser.getUserName());
    }
}
