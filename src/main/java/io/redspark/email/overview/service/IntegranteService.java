package io.redspark.email.overview.service;

import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.enums.StatusFase;
import io.redspark.email.overview.repository.EntregaSprintRepository;
import io.redspark.email.overview.repository.IntegranteRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegranteService {
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	@Autowired
	private EntregaSprintRepository entregaSprintRepository;
	
	@Autowired
	private PremioService premioService;
	
	public Integer retornaQuantidadeTokensGanha(Integrante integrante){
		Integer tokens = 0;
		List<EntregaSprint> entregaIntegrantes = entregaSprintRepository.findByIntegrante(integrante);
		
		for (EntregaSprint es : entregaIntegrantes) {
			FaseProjeto fase = es.getFase();
			if (fase.getStatus().equals(StatusFase.EM_ANDAMENTO)) {
				tokens++;
			}else if (fase.getStatus().equals(StatusFase.ENTREGUE)){
				tokens += 2;
			}
		}
		return tokens;
	}
	
	public Integer retornaQuantidadeTokensAtual(Integrante integrante){
		Integer sparksGanho = retornaQuantidadeTokensGanha(integrante);
		Integer sparksgasto = premioService.retornaValorGastoPorIntegrante(integrante);
		return sparksGanho-sparksgasto;
	}
}
