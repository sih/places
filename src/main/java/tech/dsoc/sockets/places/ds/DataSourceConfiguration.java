package tech.dsoc.sockets.places.ds;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfiguration {
	
	private static final long FIVE_MINS_IN_MS = 5*60*1000;
	// TODO make this less mysql specific
	private static final String VALIDATION_QUERY = "SELECT 1";
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.usr}")
	private String user;
	
//	@Value("${db.psw}")
	private String password;
	
	@Bean(destroyMethod = "close")
    public javax.sql.DataSource datasource() {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(url);
        ds.setUsername(user);
//        ds.setPassword(password);
        ds.setInitialSize(5);
        ds.setMaxActive(5);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        ds.setValidationInterval(FIVE_MINS_IN_MS);
        ds.setValidationQuery(VALIDATION_QUERY);
        ds.setTestOnBorrow(true);
        ds.setTestWhileIdle(true);
        return ds;
    }
 
    @Bean
    public JdbcTemplate jdbc() {
        return new JdbcTemplate(datasource());
    }

}
