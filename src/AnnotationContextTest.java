import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class AnnotationContextTest {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);

        DaoTestCode.baseDaoTestCode(ac);
    }
}
