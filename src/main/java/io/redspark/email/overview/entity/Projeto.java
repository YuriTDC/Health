package io.redspark.email.overview.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PROJETO")
public class Projeto extends AbstractEntity {

	@Column(name="NOME")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="CLIENTE_ID")
	private Cliente cliente;
	
	@ManyToMany
	@JoinTable(name = "PROJETO_INTEGRANTE", joinColumns = @JoinColumn(name = "PROJETO_ID"), inverseJoinColumns = @JoinColumn(name = "INTEGRANTE_ID"))
	private List<Integrante> integrantes = new ArrayList<Integrante>();

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the integrantes
	 */
	public List<Integrante> getIntegrantes() {
		return integrantes;
	}

	/**
	 * @param integrantes the integrantes to set
	 */
	public void setIntegrantes(List<Integrante> integrantes) {
		this.integrantes = integrantes;
	}

	public boolean addIntegrante(Integrante i) {
		return this.getIntegrantes().add(i);
	}
	
}
