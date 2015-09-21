package io.redspark.email.overview.init;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config/${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@Import({ AppDataSource.class, AppPersistenceConfig.class , AppSecurityConfig.class})
public class AppConfig {}
