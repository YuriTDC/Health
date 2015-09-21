package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.Repositorio;

import org.springframework.data.repository.CrudRepository;

public interface RepositorioRepository extends CrudRepository<Repositorio, Long>{

	Repositorio findByRepositoryGitId(Long repositoryGitId);
	

}
