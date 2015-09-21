package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Integrante;

import java.time.LocalDateTime;

import aleph.AbstractBuilder;
import aleph.BuildStep;

public class EntregaSprintBuilder extends AbstractBuilder<EntregaSprint> {

	public static EntregaSprintBuilder entregaSprint(FaseProjeto faseProjeto) {
		return new EntregaSprintBuilder()
		.faseProjeto(faseProjeto)
		.entregue(false)
		.data(LocalDateTime.now());

	}

	public EntregaSprintBuilder faseProjeto(FaseProjeto fase) {
		return set("fase", fase);
	}

	public EntregaSprintBuilder entregue(Boolean entregue) {
		return set("entregue", entregue);
	}
	
	public EntregaSprintBuilder data(LocalDateTime data) {
		return set("dataEntrega", data);
	}
	
	public EntregaSprintBuilder integrantes(final Integrante... integrantes){
		return this.add(new BuildStep<EntregaSprint>() {

			@Override
			public void build(EntregaSprint target) {
				for (Integrante i : integrantes) {
					target.getIntegrantes().add(i);				}
			}
		});

	}
}
