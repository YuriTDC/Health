package io.redspark.email.overview.dto;

import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.enums.StatusFase;

public class FaseProjetoDTO {

	private Long id;
	
	private Long repositorioId;

	private StatusFase status;

	private String nome;

	public FaseProjetoDTO(FaseProjeto faseProjeto) {
		this.id = faseProjeto.getId();
		this.repositorioId = faseProjeto.getRepositorio().getId();
		this.setStatus(faseProjeto.getStatus());
		this.nome = faseProjeto.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRepositorioId() {
		return repositorioId;
	}

	public void setRepositorioId(Long repositorioId) {
		this.repositorioId = repositorioId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatusFase getStatus() {
		return status;
	}

	public void setStatus(StatusFase status) {
		this.status = status;
	}



}
