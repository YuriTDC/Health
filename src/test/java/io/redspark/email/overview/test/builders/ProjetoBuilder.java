package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Cliente;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Projeto;
import aleph.AbstractBuilder;
import aleph.BuildStep;

public class ProjetoBuilder extends AbstractBuilder<Projeto>{

	public static ProjetoBuilder projeto(Cliente cliente){
		return new ProjetoBuilder()
			.nome("Projeto")
			.cliente(cliente);
	}
	
	public ProjetoBuilder nome(String nome){
		return set("nome", nome);
	}
	
	public ProjetoBuilder cliente(Cliente cliente){
		return set("cliente", cliente);
	}
	
	public ProjetoBuilder valores(final Integrante... integrantes){
		return this.add(new BuildStep<Projeto>() {

			@Override
			public void build(Projeto target) {
				for (Integrante i : integrantes) {
					target.addIntegrante(i);
				}
			}
		});
	}
	
}
