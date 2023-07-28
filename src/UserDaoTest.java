import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {


    @Test
    public void addAndGet() throws SQLException {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao userDao = ac.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        User addUser = new User();
        addUser.setId("semin");
        addUser.setUserName("최세민");
        addUser.setPassword("1010");

        userDao.add(addUser);
        assertEquals(1, userDao.getCount());


        User getUser = userDao.getById("semin");

        assertEquals(addUser.getUserName(), getUser.getUserName());
        assertEquals(addUser.getPassword(), getUser.getPassword());
    }
}
