package io.redspark.email.overview.init;

import java.util.Properties;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.service.WatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = { "io.redspark.email.overview.controller" , "io.redspark.email.overview.jobs" , "io.redspark.email.overview.service"})
public class AppWebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;
	
	private static final String USUARIO_GIT = "usuario.git";
	
	private static final String PASSWORD_GIT = "password.git";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("styles/**").addResourceLocations("site/dist/styles/");
		registry.addResourceHandler("scripts/**").addResourceLocations("site/dist/scripts/");
		registry.addResourceHandler("views/**").addResourceLocations("site/dist/views/");
		registry.addResourceHandler("fonts/**").addResourceLocations("site/dist/fonts/");
		registry.addResourceHandler("images/**").addResourceLocations("site/dist/images/");
		registry.addResourceHandler("bower_components/**").addResourceLocations("site/dist/bower_components/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {

		CommonsMultipartResolver config = new CommonsMultipartResolver();
		config.setDefaultEncoding("UTF-8");

		return config;
	}

	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setSuffix(".html");
		return resolver;
	}
	
	@Bean
	public JavaMailSender mailSender() {

		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(env.getRequiredProperty("email-host"));
		javaMailSender.setPort(env.getRequiredProperty("email-port", Integer.class));
		javaMailSender.setPassword(env.getRequiredProperty("email-password"));
		javaMailSender.setUsername(env.getRequiredProperty("email-username"));
		javaMailSender.setJavaMailProperties(getJavaMailProperties());

		return javaMailSender;
	}
	
	private Properties getJavaMailProperties() {

		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", false);
		properties.put("mail.smtp.starttls.required", false);

		return properties;
	}

	@Bean
	public RepositoryService repositoryService() {
		
		RepositoryService repositoryService = new RepositoryService();
		repositoryService.getClient().setCredentials(env.getRequiredProperty(USUARIO_GIT), env.getRequiredProperty(PASSWORD_GIT));
		repositoryService.getClient().setOAuth2Token("c2e3411d32a37ed288fec6b6b1a774fef9cb4e1b");
		return repositoryService;
	}

	@Bean
	public IssueService issueService() {
		
		IssueService issueService = new IssueService();
		issueService.getClient().setCredentials(env.getRequiredProperty(USUARIO_GIT), env.getRequiredProperty(PASSWORD_GIT));
		issueService.getClient().setOAuth2Token("c2e3411d32a37ed288fec6b6b1a774fef9cb4e1b");
		return issueService;
	}
	
	@Bean
	public UserService userService() {
		
		UserService userService = new UserService();
		userService.getClient().setCredentials(env.getRequiredProperty(USUARIO_GIT), env.getRequiredProperty(PASSWORD_GIT));
		return userService;
	}

	@Bean
	public WatcherService watcherService() {
		
		WatcherService watcherService = new WatcherService();
		watcherService.getClient().setCredentials(env.getRequiredProperty(USUARIO_GIT), env.getRequiredProperty(PASSWORD_GIT));
		return watcherService;
	}

	@Bean
	public MilestoneService milestoneService() {
		
		MilestoneService milestoneService = new MilestoneService();
		milestoneService.getClient().setCredentials(env.getRequiredProperty(USUARIO_GIT), env.getRequiredProperty(PASSWORD_GIT));
		return milestoneService;
	}
	
	@Bean
	public GitHubClient gitHubClient() {
		
		GitHubClient client = new GitHubClient();
		client.setCredentials(env.getRequiredProperty(USUARIO_GIT), env.getRequiredProperty(PASSWORD_GIT));
		client.setOAuth2Token("c2e3411d32a37ed288fec6b6b1a774fef9cb4e1b");
		return client;
	}
}
