package io.redspark.email.overview.dto;

import io.redspark.email.overview.entity.TrocaPremio;
import io.redspark.email.overview.json.DateTimeJsonSerializer;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TrocaPremioDTO {
	
	private Long id;
	
	private String integranteNome;
	
	private String premioNome;
	
	private Long integranteId;
	
	private Long premioId;
	
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	private LocalDateTime dataTroca;

	public TrocaPremioDTO(TrocaPremio trocaPremio) {
		this.id = trocaPremio.getId();
		this.setIntegranteNome(trocaPremio.getIntegrante().getNome());
		this.setPremioNome(trocaPremio.getPremio().getNome());
		this.setIntegranteId(trocaPremio.getIntegrante().getId());
		this.setPremioId(trocaPremio.getPremio().getId());
		this.dataTroca = trocaPremio.getDataTroca();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataTroca() {
		return dataTroca;
	}

	public void setDataTroca(LocalDateTime dataTroca) {
		this.dataTroca = dataTroca;
	}

	public String getPremioNome() {
		return premioNome;
	}

	public void setPremioNome(String premioNome) {
		this.premioNome = premioNome;
	}

	public String getIntegranteNome() {
		return integranteNome;
	}

	public void setIntegranteNome(String integranteNome) {
		this.integranteNome = integranteNome;
	}

	public Long getIntegranteId() {
		return integranteId;
	}

	public void setIntegranteId(Long integranteId) {
		this.integranteId = integranteId;
	}

	public long getPremioId() {
		return premioId;
	}

	public void setPremioId(long premioId) {
		this.premioId = premioId;
	}
}
