import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTestWithJunit4 {

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


        User getUser = userDao.getById("semin");

        Assert.assertEquals(addUser.getUserName(), getUser.getUserName());
        Assert.assertEquals(addUser.getPassword(), getUser.getPassword());
    }
}
