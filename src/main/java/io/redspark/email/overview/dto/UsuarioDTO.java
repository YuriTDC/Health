package io.redspark.email.overview.dto;

import io.redspark.email.overview.entity.Usuario;

public class UsuarioDTO {

	private Long id;

	private String nome;

	private String senha;

	private String email;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Usuario user) {
		super();
		this.id = user.getId() != null ? user.getId() : null;
		this.nome = user.getNome();
		this.senha = user.getSenha();
		this.email = user.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
