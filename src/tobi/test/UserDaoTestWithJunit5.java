package tobi.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import tobi.EmptyResultDataAccess;
import tobi.User;
import tobi.UserDao;

import java.sql.SQLException;



public class UserDaoTestWithJunit5 {

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    public void setUp() {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("tobi/applicationContext.xml");

        userDao = ac.getBean("userDao", UserDao.class);

        user1 = new User("semin", "최세민", "1010");
        user2 = new User("tobi", "토비", "0729");
        user3 = new User("younghan", "영한", "0729");
    }

    @AfterEach
    public void destroy() throws SQLException {
        userDao.deleteAll();
    }


    @Test
    public void addAndGet() throws SQLException {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        User getUser = userDao.getById("semin");

        Assertions.assertEquals(user1.getUserName(), getUser.getUserName());
        Assertions.assertEquals(user1.getPassword(), getUser.getPassword());
    }

    @Test
    public void count() throws SQLException {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        Assertions.assertEquals(3, userDao.getCount());
    }

    @Test
    public void notPresentGet() {
        Assertions.assertThrows(EmptyResultDataAccess.class, () -> userDao.getById("not_present"));
    }
}
