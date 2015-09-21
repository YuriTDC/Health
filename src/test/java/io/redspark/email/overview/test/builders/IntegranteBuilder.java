package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Integrante;
import aleph.AbstractBuilder;

public class IntegranteBuilder extends AbstractBuilder<Integrante>{

	public static IntegranteBuilder integrante(){
		return new IntegranteBuilder().nome("João").email("joao@redspark.io");
	}
	
	public IntegranteBuilder nome(String nome){
		return set("nome", nome);
	}
	
	public IntegranteBuilder email(String email){
		return set("email", email);
	}
	
	public IntegranteBuilder avatar(String avatar){
		return set("avatar", avatar);
	}
	
}
