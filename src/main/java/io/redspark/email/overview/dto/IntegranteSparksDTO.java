package io.redspark.email.overview.dto;

public class IntegranteSparksDTO {

	private Long id;
	private String nome;
	private String email;
	private Integer sparks;
	private Integer sparksRestantes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSparks() {
		return sparks;
	}

	public void setSparks(Integer sparks) {
		this.sparks = sparks;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSparksRestantes() {
		return sparksRestantes;
	}

	public void setSparksRestantes(Integer sparksRestantes) {
		this.sparksRestantes = sparksRestantes;
	}

}
