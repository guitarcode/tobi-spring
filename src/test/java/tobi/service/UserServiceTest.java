package tobi.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import tobi.dao.UserDao;
import tobi.domain.Level;
import tobi.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tobi.service.DefaultUserLevelUpgradePolicy.MIN_LOGIN_COUNT_FOR_SILVER;
import static tobi.service.DefaultUserLevelUpgradePolicy.MIN_RECOMMEND_COUNT_FOR_GOLD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/applicationContextTest.xml"})
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserLevelUpgradePolicy userLevelUpgradePolicy;
    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager transactionManager;

    List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("semin", "최세민", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
                new User("tobi", "토비", "p2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
                new User("younghan", "영한", "p3", Level.SILVER, 60, MIN_RECOMMEND_COUNT_FOR_GOLD - 1),
                new User("spring", "스프링", "p4", Level.SILVER, 60, MIN_RECOMMEND_COUNT_FOR_GOLD),
                new User("java", "자바", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @AfterEach
    public void destroy() {
        userDao.deleteAll();
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void upgradeLevels() {
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3),true);
        checkLevelUpgraded(users.get(4), false);
    }

    @Test
    void upgradeAllOrNothing() {
        userLevelUpgradePolicy = new TestUserLevelUpgradePolicy(userDao, users.get(3).getId());
        userService = new UserService(userDao, userLevelUpgradePolicy, transactionManager);
        for (User user : users) {
            userDao.add(user);
        }

        try {
            userService.upgradeLevels();
        } catch (TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());

        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().getNext());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    void add() {
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);


        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelAdd = userDao.get(userWithLevel.getId());
        User userWithoutLevelAdd = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelAdd.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelAdd.getLevel()).isEqualTo(Level.BASIC);
    }

    static class TestUserLevelUpgradePolicy extends DefaultUserLevelUpgradePolicy {
        String id;

        public TestUserLevelUpgradePolicy(UserDao userDao, String id) {
            super(userDao);
            this.id = id;
        }

        @Override
        public void upgradeLevel(User user) {
            if(id.equals(user.getId())) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }
}
