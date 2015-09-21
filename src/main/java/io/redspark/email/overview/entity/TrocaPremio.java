package io.redspark.email.overview.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TROCA_DE_PREMIO")
public class TrocaPremio extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "INTEGRANTE_ID")
	private Integrante integrante;

	@ManyToOne
	@JoinColumn(name = "PREMIO_ID")
	private Premio premio;

	@Column(name = "DATA_TROCA")
	private LocalDateTime dataTroca = LocalDateTime.now();

	public Premio getPremio() {
		return premio;
	}

	public void setPremio(Premio premio) {
		this.premio = premio;
	}

	public Integrante getIntegrante() {
		return integrante;
	}

	public void setIntegrante(Integrante integrante) {
		this.integrante = integrante;
	}

	public LocalDateTime getDataTroca() {
		return dataTroca;
	}

	public void setDataTroca(LocalDateTime dataTroca) {
		this.dataTroca = dataTroca;
	}

}
