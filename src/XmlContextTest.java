import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class XmlContextTest {
    public static void main(String[] args) throws SQLException {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("applicationContext.xml");

        DaoTestCode.baseDaoTestCode(ac);
    }
}
