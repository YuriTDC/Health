package io.redspark.email.overview.init;

import static io.redspark.email.overview.init.AppProfile.DEV;
import static io.redspark.email.overview.init.AppProfile.PROD;
import static io.redspark.email.overview.init.AppProfile.QA;
import static io.redspark.email.overview.init.AppProfile.TEST;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class AppDataSource {

	@Profile({ DEV, PROD, QA })
	public static class CommomDataSource {

		private static final String DB_URL = "db.url";
		private static final String DB_DRIVER = "db.driver";
		private static final String DB_USERNAME = "db.username";
		private static final String DB_PASSWORD = "db.password";

		private static final int MIN_POOL_SIZE = 1;
		private static final int IDLE_TEST_TEST_TIME = 1800;

		@Autowired
		private Environment env;

		@Bean
		public DataSource dataSource() throws PropertyVetoException {

			ComboPooledDataSource dataSource = new ComboPooledDataSource();
			dataSource.setJdbcUrl(env.getProperty(DB_URL));
			dataSource.setUser(env.getProperty(DB_USERNAME));
			dataSource.setPassword(env.getProperty(DB_PASSWORD));
			dataSource.setDriverClass(env.getProperty(DB_DRIVER));
			dataSource.setMinPoolSize(MIN_POOL_SIZE);
			dataSource.setIdleConnectionTestPeriod(IDLE_TEST_TEST_TIME);

			return dataSource;
		}
	}

	@Profile({ TEST })
	public static class TestDataSource {
		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();
		}
	}
}
