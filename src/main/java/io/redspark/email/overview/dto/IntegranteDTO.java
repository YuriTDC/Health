package io.redspark.email.overview.dto;

import io.redspark.email.overview.entity.Integrante;

public class IntegranteDTO {

	private Long id;
	
	private String nome;
	
	public IntegranteDTO(){}
	public IntegranteDTO(Integrante integrante){
		this.id = integrante.getId();
		this.nome = integrante.getNome();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
