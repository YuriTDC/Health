package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Premio;
import aleph.AbstractBuilder;

public class PremioBuilder extends AbstractBuilder<Premio>{
	
	public static PremioBuilder premio(){
		return new PremioBuilder()
		.nome("premio")
		.valor(10);
	}
	
	public PremioBuilder nome(String nome){
		return set("nome", nome);
	}
	
	public PremioBuilder valor(Integer valor){
		return set("valor", valor);
	}
}
