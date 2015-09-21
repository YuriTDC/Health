package io.redspark.email.overview.security;

import io.redspark.email.overview.entity.Usuario;
import io.redspark.email.overview.repository.UsuarioRepository;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class EmailOverviewSecurityContext implements ApplicationContextAware {

	private static ApplicationContext context;

	public static Usuario getLoggedUser(){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UsuarioRepository bean = context.getBean(UsuarioRepository.class);
		Usuario usuario = bean.findByUsuario(authentication.getName());

		return usuario;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
