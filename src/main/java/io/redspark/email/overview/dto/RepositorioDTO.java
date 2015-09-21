package io.redspark.email.overview.dto;

import io.redspark.email.overview.entity.Repositorio;

public class RepositorioDTO {

	private Long id;

	private Long repositoryGitId;
	
	public RepositorioDTO(Repositorio rep){
		this.id = rep.getId();
		this.repositoryGitId = rep.getRepositoryGitId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRepositoryGitId() {
		return repositoryGitId;
	}

	public void setRepositoryGitId(Long repositoryGitId) {
		this.repositoryGitId = repositoryGitId;
	}

}
