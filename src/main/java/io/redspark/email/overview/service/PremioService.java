package io.redspark.email.overview.service;

import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.TrocaPremio;
import io.redspark.email.overview.repository.TrocaPremioRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremioService {

	@Autowired
	private TrocaPremioRepository trocaPremioRepository;
	
	public Integer retornaValorGastoPorIntegrante(Integrante integrante){
		List<TrocaPremio> premios = trocaPremioRepository.findByIntegrante(integrante);
		Integer sparks = 0;
		
		for (TrocaPremio premio : premios) {
			sparks += premio.getPremio().getValor();
			
		}
		
		return sparks;
	}
	
	

}
