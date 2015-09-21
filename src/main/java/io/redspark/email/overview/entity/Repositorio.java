package io.redspark.email.overview.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table( name = "REPOSITORIO")
public class Repositorio extends AbstractEntity {
	
	@Column( name = "NOME")
	private String nome;
	
	@Column( name = "REPOSITORY_GIT_ID")
	private Long repositoryGitId;
	
	@Column( name = "NOTIFICAR")
	private Boolean notificar = false;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "REPOSITORIO_CLIENTE", joinColumns = @JoinColumn(name = "REPOSITORIO_ID"), inverseJoinColumns = @JoinColumn(name = "CLIENTE_ID"))
	private Set<Cliente> clientes = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "REPOSITORIO_TIME", joinColumns = @JoinColumn(name = "REPOSITORIO_ID"), inverseJoinColumns = @JoinColumn(name = "TIME_ID"))
	private Set<Integrante> time = new HashSet<>();
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getRepositoryGitId() {
		return repositoryGitId;
	}

	public void setRepositoryGitId(Long repositoryGitId) {
		this.repositoryGitId = repositoryGitId;
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Set<Integrante> getTime() {
		return time;
	}

	public void setTime(Set<Integrante> time) {
		this.time = time;
	}

	public Boolean getNotificar() {
		return notificar;
	}

	public void setNotificar(Boolean notificar) {
		this.notificar = notificar;
	}

}
