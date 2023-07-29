package tobi.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import tobi.EmptyResultDataAccess;
import tobi.User;
import tobi.UserDao;

import java.sql.SQLException;


public class UserDaoTestWithJunit5 {


    @Test
    public void addAndGet() throws SQLException {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("tobi/applicationContext.xml");

        UserDao userDao = ac.getBean("userDao", UserDao.class);

        userDao.deleteAll();
        Assertions.assertEquals(0, userDao.getCount());

        User addUser1 = new User("semin", "최세민", "1010");
        User addUser2 = new User("tobi", "토비", "0729");

        userDao.add(addUser1);
        userDao.add(addUser2);
        Assertions.assertEquals(2, userDao.getCount());


        User getUser = userDao.getById("semin");

        Assertions.assertEquals(addUser1.getUserName(), getUser.getUserName());
        Assertions.assertEquals(addUser1.getPassword(), getUser.getPassword());
    }

    @Test
    public void count() throws SQLException {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("tobi/applicationContext.xml");

        UserDao userDao = ac.getBean("userDao", UserDao.class);

        userDao.deleteAll();
        Assertions.assertEquals(0, userDao.getCount());

        User addUser1 = new User("semin", "최세민", "1010");
        User addUser2 = new User("tobi", "토비", "0729");

        userDao.add(addUser1);
        userDao.add(addUser2);
        Assertions.assertEquals(2, userDao.getCount());
    }

    @Test
    public void notPresentGet() throws SQLException {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("tobi/applicationContext.xml");

        UserDao userDao = ac.getBean("userDao", UserDao.class);

        Assertions.assertThrows(EmptyResultDataAccess.class, () -> userDao.getById("not_present"));
    }
}
