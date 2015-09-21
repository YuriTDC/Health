package io.redspark.email.overview.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PREMIO")
public class Premio extends AbstractEntity {
	
	@NotNull
	@Column(name="NOME")
	private String nome;
	
	@NotNull
	@Column(name="VALOR")
	private Integer valor;
	
	@OneToMany(mappedBy = "premio", fetch = FetchType.EAGER)
	private List<TrocaPremio> trocaPremio = new ArrayList<>();
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public List<TrocaPremio> getTrocaPremio() {
		return trocaPremio;
	}
	public void setTrocaPremio(List<TrocaPremio> trocaPremio) {
		this.trocaPremio = trocaPremio;
	}
	
}
