package tobi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class CountingConnectionDaoFactory {
    @Bean
    public JdbcUserDao userDao() {
        return new JdbcUserDao(dataSource());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new SimpleDriverDataSource();
    }
}
