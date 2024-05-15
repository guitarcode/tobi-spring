package tobi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/applicationContextTest.xml"})
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void bean() {
        Assertions.assertThat(userService).isNotNull();
    }
}
