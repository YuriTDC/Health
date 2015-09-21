package io.redspark.email.overview.form;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.redspark.email.overview.enums.StatusDados;
import io.redspark.email.overview.json.LocalDateJsonDeserializer;

public class DadosProjetoForm {
	
	private StatusDados status;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateJsonDeserializer.class)
	private LocalDate dataInicio;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateJsonDeserializer.class)
	private LocalDate dataTermino;
	
	private Long repositorioId;
	
	public StatusDados getStatus() {
		return status;
	}

	public void setDados(StatusDados status) {
		this.status = status;
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




}
