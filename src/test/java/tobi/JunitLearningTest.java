package tobi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class JunitLearningTest {
    static Set<JunitLearningTest> junitLearningTestInstanceSet = new HashSet<>();

    @Test
    public void test1() {
        Assertions.assertFalse(junitLearningTestInstanceSet.contains(this));
        junitLearningTestInstanceSet.add(this);
    }

    @Test
    public void test2() {
        Assertions.assertFalse(junitLearningTestInstanceSet.contains(this));
        junitLearningTestInstanceSet.add(this);
    }

    @Test
    public void test3() {
        Assertions.assertFalse(junitLearningTestInstanceSet.contains(this));
        junitLearningTestInstanceSet.add(this);
    }
}
