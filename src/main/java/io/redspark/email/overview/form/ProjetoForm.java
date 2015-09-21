package io.redspark.email.overview.form;

import java.util.List;

public class ProjetoForm {

	private String nome;
	private Long clienteId;
	private List<Long> integrantes;
	
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
	 * @return the clienteId
	 */
	public Long getClienteId() {
		return clienteId;
	}
	/**
	 * @param clienteId the clienteId to set
	 */
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	/**
	 * @return the integrantes
	 */
	public List<Long> getIntegrantes() {
		return integrantes;
	}
	/**
	 * @param integrantes the integrantes to set
	 */
	public void setIntegrantes(List<Long> integrantes) {
		this.integrantes = integrantes;
	}
	
	
}
