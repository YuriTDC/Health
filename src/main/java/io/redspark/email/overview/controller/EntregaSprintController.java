package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.dto.EntregaSprintDTO;
import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.exceptions.EmailOverviewException;
import io.redspark.email.overview.form.EntregaSprintForm;
import io.redspark.email.overview.repository.EntregaSprintRepository;
import io.redspark.email.overview.repository.FaseProjetoRepository;
import io.redspark.email.overview.repository.IntegranteRepository;

@RestController
@RequestMapping(value = "/entregaSprint")
public class EntregaSprintController {

	@Autowired
	private FaseProjetoRepository faseProjetoRepository;

	@Autowired
	private EntregaSprintRepository repository;
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	@RequestMapping(method=GET)
	@Transactional(readOnly=true)
	public List<EntregaSprintDTO> list(){
		List<EntregaSprint> entregaSprint = (List<EntregaSprint>) repository.findAll();
		 return entregaSprint.stream()
				 .map(EntregaSprintDTO::new)
				 .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/{id}", method = GET)
	public EntregaSprintDTO findById(@PathVariable("id") Long id) {
		return new EntregaSprintDTO(repository.findOne(id));
	}
	

	@Transactional(readOnly=true)
	@RequestMapping(value = "/fase/{faseId}", method = GET)
	public List<EntregaSprintDTO> recuperarSprintsFase(@PathVariable("faseId") Long faseId){
		
		FaseProjeto fase = faseProjetoRepository.findOne(faseId);
		
		List<EntregaSprint> entregaSprint = (List<EntregaSprint>) repository.findByFase(fase);
			
		 return entregaSprint.stream()
				 .map(EntregaSprintDTO::new)
				 .collect(Collectors.toList());
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public EntregaSprint create(@RequestBody EntregaSprintForm form) {
		
		EntregaSprint entregaSprint = new EntregaSprint();
		
		entregaSprint.setFase(faseProjetoRepository.findOne(form.getFaseId()));
		entregaSprint.setDataEntrega(form.getDataEntrega());
		entregaSprint.setEntregue(form.getEntregue());
		
		Set<Integrante> integrantes = new HashSet<Integrante>(); 
		for(Long integranteId : form.getIntegrantes()){
			integrantes.add(integranteRepository.findOne(integranteId));
		}
		entregaSprint.setIntegrantes(integrantes);
		
		entregaSprint = repository.save(entregaSprint);

		return entregaSprint;
	}

	@RequestMapping(value = "/{id}", method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public EntregaSprint update(@PathVariable("id") Long id, @RequestBody EntregaSprintForm form) {
		
		EntregaSprint entregaSprint = repository.findOne(id);
		
		entregaSprint.setFase(faseProjetoRepository.findOne(form.getFaseId()));
		entregaSprint.setDataEntrega(form.getDataEntrega());
		entregaSprint.setEntregue(form.getEntregue());
		
		Set<Integrante> integrantes = new HashSet<Integrante>(); 
		for(Long integranteId : form.getIntegrantes()){
			integrantes.add(integranteRepository.findOne(integranteId));
		}
		entregaSprint.setIntegrantes(integrantes);
		
		entregaSprint = repository.save(entregaSprint);

		return entregaSprint;
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	@Transactional
	public List<EntregaSprint> delete(@PathVariable("id") Long id) {
		
		List<EntregaSprint> findAll = new ArrayList<EntregaSprint>();
		try {
			
			repository.delete(id);
			findAll = (List<EntregaSprint>) repository.findAll();
		} catch (Exception e) {
			throw new EmailOverviewException(HttpStatus.PRECONDITION_FAILED, "Este Sprint está associado a uma Fase.");
		}
		
		
		return findAll;
	}
	

}
