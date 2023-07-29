package tobi.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobi.CountingConnectionDaoFactory;
import tobi.CountingConnectionMaker;
import tobi.DaoTestCode;

import java.sql.SQLException;

public class CountingConnectionTest {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(CountingConnectionDaoFactory.class);

        DaoTestCode.baseDaoTestCode(ac);

        CountingConnectionMaker connectionMaker = ac.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println(connectionMaker.getConnectionCount());

    }
}
