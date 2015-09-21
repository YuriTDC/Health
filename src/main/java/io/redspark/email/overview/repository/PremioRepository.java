package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.Premio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PremioRepository extends CrudRepository<Premio, Long> {
	
	@Query("SELECT p FROM Premio p JOIN  p.trocaPremio tp WHERE p.id = tp.premio.id GROUP BY p ORDER BY count(tp) DESC")
	List<Premio> findMaisTrocado(); 

}
