package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Premio;
import io.redspark.email.overview.entity.TrocaPremio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TrocaPremioRepository extends CrudRepository<TrocaPremio, Long>{

	List<TrocaPremio> findByIntegrante(Integrante integrante);
	
	List<TrocaPremio> findByPremio(Premio premio);

	@Query("SELECT p FROM Premio p JOIN  p.trocaPremio tp WHERE p.id = tp.premio.id GROUP BY p ORDER BY count(tp) DESC")
	List<Premio> listaPremiosMaisTrocados();
	
}
