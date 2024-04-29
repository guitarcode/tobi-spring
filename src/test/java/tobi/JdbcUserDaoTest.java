package tobi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/applicationContextTest.xml"})
public class JdbcUserDaoTest {

    @Autowired
    UserDao userDao;
    @Autowired
    DataSource dataSource;

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

        assertThat(getUser.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user1.getPassword()).isEqualTo(getUser.getPassword());
    }

    @Test
    public void count() {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);

        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    public void notPresentGet() {
        assertThatThrownBy(() -> userDao.get("not_present"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void duplicateKey() {
        userDao.add(user1);


        assertThatThrownBy(() -> userDao.add(user1))
                .isInstanceOf(DataAccessException.class);
    }
}
