package io.redspark.email.overview.dto;

import io.redspark.email.overview.entity.Projeto;

public class ProjetoDTO {

	private Long id;
	private String nome;
	private Long clienteId;
	private String clienteNome;
	
	public ProjetoDTO() {}
	
	public ProjetoDTO(Projeto projeto){
		this.id = projeto.getId();
		this.nome = projeto.getNome();
		this.clienteId = projeto.getCliente().getId();
		this.clienteNome = projeto.getCliente().getNome();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
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
	 * @return the clienteNome
	 */
	public String getClienteNome() {
		return clienteNome;
	}

	/**
	 * @param clienteNome the clienteNome to set
	 */
	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}
	
	
	
}
