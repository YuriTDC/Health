package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Integrante;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EntregaSprintRepository extends CrudRepository<EntregaSprint, Long>{
	
	List<EntregaSprint> findByFase(FaseProjeto fase);
	
	List<EntregaSprint> findByFaseAndEntregueTrue(FaseProjeto fase);
	
	@Query("select e from EntregaSprint e join e.integrantes i where e.entregue = true and i = ?1")
	List<EntregaSprint> findByIntegrante(Integrante integrante);
	
}
