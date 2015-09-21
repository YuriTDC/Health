package io.redspark.email.overview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;

import io.redspark.email.overview.enums.StatusDados;

@Entity
@Table(name="DADOS_PROJETO")
public class DadosProjeto extends AbstractEntity{

	@ManyToOne
	@JoinColumn(name="REPOSITORIO_ID")
	private Repositorio repositorio;
	
	@Column(name="DATA_INICIO")
	private LocalDate dataInicio;
	
	@Column(name="DATA_TERMINO")
	private LocalDate dataTermimo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="DADOS")
	private StatusDados status;
		

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataTermimo() {
		return dataTermimo;
	}

	public void setDataTermimo(LocalDate dataTermimo) {
		this.dataTermimo = dataTermimo;
	}

	public StatusDados getStatus() {
		return status;
	}

	public void setStatus(StatusDados status) {
		this.status = status;
	}



}
