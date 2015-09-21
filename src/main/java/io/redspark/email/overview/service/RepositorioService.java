package io.redspark.email.overview.service;

import org.eclipse.egit.github.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.repository.ClienteRepository;
import io.redspark.email.overview.repository.IntegranteRepository;
import io.redspark.email.overview.repository.RepositorioRepository;

@Service
public class RepositorioService {

	@Autowired
	private RepositorioRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	
	public Repositorio checkRemoteRepositoryWithLocalRepository(Repository gitRepository, Repositorio repositorio) {
		
		if (repositorio == null) {
			
			repositorio = new Repositorio();
			repositorio.setNome(gitRepository.getName());
			repositorio.setRepositoryGitId(gitRepository.getId());
			
			repositorio = repository.save(repositorio);
		}
		
		return repositorio;
	}
}
