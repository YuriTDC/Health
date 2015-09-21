package io.redspark.email.overview.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario extends AbstractEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "USUARIO")
	private String usuario;
	
	@Column(name = "SENHA")
	private String senha;
	
	@Column(name = "EMAIL")
	private String email;
	
	public Usuario(String nome, String usuario, Long matricula, String senha, String rg, String email) {

		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.usuario = usuario;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
