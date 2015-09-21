package io.redspark.email.overview.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.redspark.email.overview.entity.DadosProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.enums.StatusDados;

public interface DadosProjetoRepository extends CrudRepository<DadosProjeto, Long> {
	
	List<DadosProjeto> findByRepositorio(Repositorio repositorio);
	
	List<DadosProjeto> findByStatusIn(List<StatusDados> status);
	
		
}
