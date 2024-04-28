package tobi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/applicationContextTest.xml"})
public class JdbcUserDaoTest {

    @Autowired
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("semin", "최세민", "1010");
        user2 = new User("tobi", "토비", "0729");
        user3 = new User("younghan", "영한", "0729");
    }

    @AfterEach
    public void destroy() {
        userDao.deleteAll();
    }


    @Test
    public void addAndGet() {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        User getUser = userDao.get("semin");

        Assertions.assertEquals(user1.getUsername(), getUser.getUsername());
        Assertions.assertEquals(user1.getPassword(), getUser.getPassword());
    }

    @Test
    public void count() {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        Assertions.assertEquals(3, userDao.getCount());
    }

    @Test
    public void notPresentGet() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> userDao.get("not_present"));
    }

    @Test
    public void duplicateKey() {
        userDao.add(user1);

        org.assertj.core.api.Assertions
                .assertThatThrownBy(() -> userDao.add(user1))
                .isInstanceOf(DataAccessException.class);
    }
}
