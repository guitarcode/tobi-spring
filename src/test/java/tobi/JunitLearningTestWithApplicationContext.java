package tobi;//package tobi.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/junitTestContext.xml")
public class JunitLearningTestWithApplicationContext {
    @Autowired
    ApplicationContext applicationContext;

    static ApplicationContext curContext;

    @Test
    public void test1() {
        Assertions.assertTrue(curContext == null || curContext == applicationContext);
        curContext = applicationContext;
    }

    @Test
    public void test2() {
        Assertions.assertTrue(curContext == null || curContext == applicationContext);
        curContext = applicationContext;
    }

    @Test
    public void test3() {
        Assertions.assertTrue(curContext == null || curContext == applicationContext);
        curContext = applicationContext;
    }
}
