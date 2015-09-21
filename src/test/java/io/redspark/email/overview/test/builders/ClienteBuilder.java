package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Cliente;
import aleph.AbstractBuilder;

public class ClienteBuilder extends AbstractBuilder<Cliente>{

	public static ClienteBuilder cliente(){
		return new ClienteBuilder().nome("Cliente");
	}
	
	public ClienteBuilder nome(String nome){
		return set("nome", nome);
	}
}
