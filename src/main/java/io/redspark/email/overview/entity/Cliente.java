package io.redspark.email.overview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "CLIENTE" )
public class Cliente extends AbstractEntity {
	
	@Column( name = "NOME") 
	private String pessoa;
	
	@Column( name = "EMAIL")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
