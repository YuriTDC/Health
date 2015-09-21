package io.redspark.email.overview.init;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableJpaRepositories(basePackages = "io.redspark.email.overview.repository")
public class AppPersistenceConfig {

	private static final String ENTITY_PACKAGE = "io.redspark.email.overview.entity";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PlatformTransactionManager transactionManager(){
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(ENTITY_PACKAGE);
		emf.setJpaProperties(getPersistenceProperties());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return emf;
	}
	
	private Properties getPersistenceProperties() {

		Properties properties = new Properties();
		properties.setProperty(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
		properties.setProperty(HIBERNATE_HBM2DDL_AUTO, env.getProperty(HIBERNATE_HBM2DDL_AUTO));

		return properties;
	}

	@Bean
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate tt = new TransactionTemplate();
        tt.setTransactionManager( this.transactionManager() );
        return tt;
    }
}
