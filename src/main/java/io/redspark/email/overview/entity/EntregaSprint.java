package io.redspark.email.overview.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name="ENTREGA_SPRINT")
public class EntregaSprint extends AbstractEntity{

	@ManyToOne
	@JoinColumn(name="FASE_ID")
	private FaseProjeto fase;
	
	@Column(name="DATA_ENTREGA")
	private LocalDate dataEntrega;
	
	@ManyToMany
	@JoinTable(name = "ENTREGA_SPRINT_INTEGRANTE", joinColumns = @JoinColumn(name = "ENTREGA_SPRINT_ID"), inverseJoinColumns = @JoinColumn(name = "INTEGRANTE_ID"))
	private Set<Integrante> integrantes = new HashSet<Integrante>();
	
	@Column(name="ENTREGUE")
	private Boolean entregue;
	
	public FaseProjeto getFase() {
		return fase;
	}

	public void setFase(FaseProjeto fase) {
		this.fase = fase;
	}

	public Set<Integrante> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(Set<Integrante> integrantes) {
		this.integrantes = integrantes;
	}

	public Boolean getEntregue() {
		return entregue;
	}

	public void setEntregue(Boolean entregue) {
		this.entregue = entregue;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

}