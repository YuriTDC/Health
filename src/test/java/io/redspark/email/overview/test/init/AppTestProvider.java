package io.redspark.email.overview.test.init;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import aleph.ChainPersistenceProvider;
import aleph.ContextUtil;
import aleph.PersistenceProvider;

@Configuration
@PropertySource("classpath:config/test.properties")
public class AppTestProvider {

	@Autowired
	private DataSource dataSource;

	@Bean
	public ContextUtil contextUtil() {
		return new ContextUtil();
	}

	@Bean
	public ChainPersistenceProvider chainPersistenceProvider() {
		return new ChainPersistenceProvider();
	}

	@Bean
	public PersistenceProvider persistenceProvider() {
		return new JpaPersistenceProvider();
	}
	
	@Bean
	public JdbcTemplate template(){
		return new JdbcTemplate(dataSource);
	}
}
