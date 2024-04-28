package tobi;

import com.mysql.cj.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DaoFactory {
    @Bean
    public DataSource dataSource() {
        try {
            return  new SimpleDriverDataSource(
                    new Driver(),
                    "jdbc:mysql://localhost/tobi",
                    "root",
                    "root"
            );
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Bean
    public JdbcUserDao userDao() {
        return new JdbcUserDao(dataSource());
    }
}
