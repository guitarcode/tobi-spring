package tobi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobi.domain.Level;
import tobi.domain.User;
import tobi.dao.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;

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
        user1 = new User("semin", "최세민", "1010", Level.BASIC, 1, 0);
        user2 = new User("tobi", "토비", "0729", Level.SILVER, 55, 10);
        user3 = new User("younghan", "영한", "0729", Level.GOLD, 100, 40);
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

        checkSameUser(user1, getUser);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
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

    @Test
    void sqlExceptionTranslate() {
        try {
            userDao.add(user1);
            userDao.add(user2);
        } catch (DuplicateKeyException exception) {
            SQLException sqlException = (SQLException) exception.getRootCause();
            SQLExceptionTranslator exceptionTranslator =
                    new SQLErrorCodeSQLExceptionTranslator(dataSource);

            assertThat(exceptionTranslator.translate(null, null, sqlException))
                    .isInstanceOf(DuplicateKeyException.class);
        }
    }
}
