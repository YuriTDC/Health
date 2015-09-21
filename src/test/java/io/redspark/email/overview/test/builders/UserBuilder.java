package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import aleph.AbstractBuilder;

public class UserBuilder extends AbstractBuilder<Usuario>{

	public static UserBuilder user() {
		return new UserBuilder()
			.usuario("overview")
			.senha("overview");
	}
	
	public UserBuilder usuario(String usuario) {
		return set("usuario", usuario);
	}
	
	public UserBuilder senha(String senha) {
		return set("senha", new BCryptPasswordEncoder().encode(senha));
	}
	
}
