package io.redspark.email.overview.dto;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.redspark.email.overview.entity.DadosProjeto;
import io.redspark.email.overview.enums.StatusDados;
import io.redspark.email.overview.json.LocalDateJsonSerializer;

public class DadosProjetoDTO {
	
	private Long id;
	
	private Long repositorioId;
	
	@JsonSerialize(using=LocalDateJsonSerializer.class)
	private LocalDate dataInicio;
	
	@JsonSerialize(using=LocalDateJsonSerializer.class)
	private LocalDate dataTermino;
	
	private StatusDados status;
	
	public DadosProjetoDTO(DadosProjeto dadosProjeto) {
		this.id = dadosProjeto.getId();
		this.setStatus(dadosProjeto.getStatus());
		this.dataInicio = dadosProjeto.getDataInicio();
		this.dataTermino = dadosProjeto.getDataTermimo();
		this.repositorioId = dadosProjeto.getRepositorio().getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}


	public Long getRepositorioId() {
		return repositorioId;
	}

	public void setRepositorioId(Long repositorioId) {
		this.repositorioId = repositorioId;
	}

	public StatusDados getStatus() {
		return status;
	}

	public void setStatus(StatusDados status) {
		this.status = status;
	}

}
