package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.enums.StatusFase;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FaseProjetoRepository extends CrudRepository<FaseProjeto, Long> {
	
	List<FaseProjeto> findByRepositorio(Repositorio repositorio);
	
	List<FaseProjeto> findByStatusIn(List<StatusFase> status);
		

}
