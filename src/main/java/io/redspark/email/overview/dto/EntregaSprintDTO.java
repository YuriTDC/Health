package io.redspark.email.overview.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.json.LocalDateJsonSerializer;

public class EntregaSprintDTO {

	private Long id;

	private Long faseId;
	
	private List<String> ints = new ArrayList<String>();

	private Boolean entregue;
	
	@JsonSerialize(using=LocalDateJsonSerializer.class)
	private LocalDate dataEntrega;

	public EntregaSprintDTO(EntregaSprint entregaSprint) {
		this.id = entregaSprint.getId();
		this.dataEntrega = entregaSprint.getDataEntrega();
		this.faseId = entregaSprint.getFase().getId();
		this.entregue = entregaSprint.getEntregue();
		for (Integrante i : entregaSprint.getIntegrantes()) {
			ints.add(i.getNome());
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEntregue() {
		return entregue;
	}

	public void setEntregue(Boolean entregue) {
		this.entregue = entregue;
	}

	public List<String> getInts() {
		return ints;
	}

	public void setInts(List<String> ints) {
		this.ints = ints;
	}

	public Long getFaseId() {
		return faseId;
	}

	public void setFaseId(Long faseId) {
		this.faseId = faseId;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
}
