package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.enums.StatusFase;
import aleph.AbstractBuilder;

public class FaseProjetoBuilder extends AbstractBuilder<FaseProjeto> {
	
	public static FaseProjetoBuilder faseProjeto(Repositorio repositorio){
		return new FaseProjetoBuilder()	
		.nome("nome")
		.repositorio(repositorio)
		.status(StatusFase.EM_ANDAMENTO);
	}
	
	public FaseProjetoBuilder nome(String nome){
		return set("nome", nome);
	}
	
	public FaseProjetoBuilder repositorio(Repositorio repositorio){
		return set("repositorio", repositorio);
	}
	
	public FaseProjetoBuilder status(StatusFase status){
		return set("status", status);
	}
	
	

}
