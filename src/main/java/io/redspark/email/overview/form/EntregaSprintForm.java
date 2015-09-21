package io.redspark.email.overview.form;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.redspark.email.overview.json.LocalDateJsonDeserializer;

public class EntregaSprintForm {

	private Long faseId;
	

	private Boolean entregue;
	
	private Set<Long> integrantes = new HashSet<Long>();
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = LocalDateJsonDeserializer.class)
	private LocalDate dataEntrega;
	
	public Long getFaseId() {
		return faseId;
	}

	public void setFaseId(Long faseId) {
		this.faseId = faseId;
	}

	public Boolean getEntregue() {
		return entregue;
	}

	public void setEntregue(Boolean entregue) {
		this.entregue = entregue;
	}

	public Set<Long> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(Set<Long> integrantes) {
		this.integrantes = integrantes;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

}
