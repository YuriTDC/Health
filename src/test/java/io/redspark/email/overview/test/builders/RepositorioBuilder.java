package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Repositorio;
import aleph.AbstractBuilder;
import aleph.BuildStep;

public class RepositorioBuilder extends AbstractBuilder<Repositorio> {
	
	public static RepositorioBuilder repositorio(){
		return new RepositorioBuilder()
		.nome("Repositorio");
	}

	public RepositorioBuilder nome(String nome){
		return set("nome", nome);
	}
	
	public RepositorioBuilder time(final Integrante... integrantes){
		return this.add(new BuildStep<Repositorio>() {

			@Override
			public void build(Repositorio target) {
				for (Integrante i : integrantes) {
					target.getTime().add(i);
				}
			}
		});
	}
}
