import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class ContextTest {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);

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
