package io.redspark.email.overview.form;

import io.redspark.email.overview.enums.StatusFase;

public class FaseProjetoForm {
	
	private String nome;
	
	private StatusFase status;
	
	private Long repositorioId;
	
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

	public Long getRepositorioId() {
		return repositorioId;
	}

	public void setRepositorioId(Long repositorioId) {
		this.repositorioId = repositorioId;
	}




}
