package io.redspark.email.overview.security;

import static java.util.Arrays.asList;
import io.redspark.email.overview.entity.Usuario;
import io.redspark.email.overview.repository.UsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	private static final String USER_NOT_FOUND = "user.not.found";

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

		logger.info("Tentando logar com usuario : " + usuario);

		Usuario user = repository.findByUsuario(usuario);
		
		if(user == null) {
			throw new UsernameNotFoundException(USER_NOT_FOUND);
		}

		return new org.springframework.security.core.userdetails.User(user.getUsuario(), user.getSenha(), asList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
