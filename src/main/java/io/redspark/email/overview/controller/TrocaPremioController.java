package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.dto.IntegranteDTO;
import io.redspark.email.overview.dto.PremioDTO;
import io.redspark.email.overview.dto.TrocaPremioDTO;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Premio;
import io.redspark.email.overview.entity.TrocaPremio;
import io.redspark.email.overview.exceptions.EmailOverviewException;
import io.redspark.email.overview.form.TrocaPremioForm;
import io.redspark.email.overview.repository.IntegranteRepository;
import io.redspark.email.overview.repository.PremioRepository;
import io.redspark.email.overview.repository.TrocaPremioRepository;


@RestController
@RequestMapping(value="/trocaPremio")
public class TrocaPremioController {
	
	@Autowired
	private TrocaPremioRepository repository;
	
	@Autowired
	private PremioRepository premioRepository;
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	@RequestMapping(method=GET)
	@Transactional(readOnly=true)
	public List<TrocaPremioDTO> list(){
		 List<TrocaPremio> trocaPremios = (List<TrocaPremio>) repository.findAll();
		 
		 return trocaPremios.stream()
			.map(TrocaPremioDTO::new)
			.collect(Collectors.toList());
	}
	
	@RequestMapping(value="/{id}", method=GET)
	@Transactional(readOnly=true)
	public TrocaPremioDTO findById(@PathVariable("id") Long id){
		TrocaPremio trocaPremio = repository.findOne(id);
		return new TrocaPremioDTO(trocaPremio);
	}

	@RequestMapping(value="/integrante/{integranteId}", method=GET)
	@Transactional(readOnly=true)
	public List<PremioDTO> listByIntegrante(@PathVariable("integranteId") Long integranteId){
		
		Integrante integrante = integranteRepository.findOne(integranteId);
		
		List<TrocaPremio> trocas = repository.findByIntegrante(integrante);
		
		List<PremioDTO> premios = new ArrayList<PremioDTO>();
		for (TrocaPremio tp : trocas) {
			premios.add(new PremioDTO(tp.getPremio()));
		}
		
		 return premios;
	}

	@RequestMapping(value="/premio/{premioId}")
	@Transactional(readOnly=true)
	public List<IntegranteDTO> listByPremio(@PathVariable("premioId") Long premioId){
		
		Premio premio = premioRepository.findOne(premioId);
		
		List<TrocaPremio> trocas = repository.findByPremio(premio);
		
		List<IntegranteDTO> integrantes = new ArrayList<IntegranteDTO>();
		for (TrocaPremio tp : trocas) {
			integrantes.add(new IntegranteDTO(tp.getIntegrante()));
		}
		
		 return integrantes;
	}
	
	@RequestMapping(value="/trocaSemPremio")
	@Transactional(readOnly = true)
	public List<IntegranteDTO> listByIntegrantesSemPremio(){
		
		List<Integrante> integrantes = (List<Integrante>) integranteRepository.findAll();
		List<TrocaPremio> trocas = (List<TrocaPremio>) repository.findAll();
		
		List<Integrante> integrantesSemPremio = new ArrayList<Integrante>();
		
		for (TrocaPremio tp : trocas) {
			integrantesSemPremio.add(tp.getIntegrante());
		}
		
		integrantes.removeAll(integrantesSemPremio);
		
		 return integrantes.stream()
			.map(IntegranteDTO::new)
			.collect(Collectors.toList());		

	}
	
	@RequestMapping(value = "/rankPremio", method = GET)
	@Transactional(readOnly = true)
	public List<PremioDTO> premiosMaisAcessados() {
		
		List<Premio> premiosTrocados = (List<Premio>) premioRepository.findMaisTrocado();
		
		return premiosTrocados.stream()
				.map(PremioDTO::new)
				.collect(Collectors.toList());

	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public TrocaPremioDTO create(@RequestBody TrocaPremioForm form){
		TrocaPremio trocaPremio = new TrocaPremio();
		trocaPremio.setPremio(premioRepository.findOne(form.getPremioId()));
		trocaPremio.setIntegrante(integranteRepository.findOne(form.getIntegranteId()));
		trocaPremio.setDataTroca(LocalDateTime.now());
		
		
		repository.save(trocaPremio);
		
		return new TrocaPremioDTO(trocaPremio);
	}
	
	@RequestMapping(value="/{id}", method=DELETE)
	@Transactional
	public List<TrocaPremio> delete(@PathVariable("id") Long id){
		
		List<TrocaPremio> findAll = new ArrayList<TrocaPremio>();
		try {
			
			repository.delete(id);
			findAll = (List<TrocaPremio>) repository.findAll();
		} catch (Exception e) {
			throw new EmailOverviewException(HttpStatus.PRECONDITION_FAILED, "Esta Troca está associado a um Integrante.");
		}
		
		
		return findAll;
	}
	
}