package io.redspark.email.overview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.redspark.email.overview.enums.StatusFase;

@Entity
@Table(name="FASE_PROJETO")
public class FaseProjeto extends AbstractEntity{

	@ManyToOne
	@JoinColumn(name="REPOSITORIO_ID")
	private Repositorio repositorio;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private StatusFase status;
	
	@Column(name="NOME")
	private String nome;
	
	public StatusFase getStatus() {
		return status;
	}

	public void setStatus(StatusFase status) {
		this.status = status;
	}

	public Repositorio getRepositorio() {
		return repositorio;
	}

	public void setRepositorio(Repositorio repositorio) {
		this.repositorio = repositorio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


}
