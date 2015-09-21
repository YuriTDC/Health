package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import io.redspark.email.overview.dto.ProjetoDTO;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Projeto;
import io.redspark.email.overview.form.ProjetoForm;
import io.redspark.email.overview.repository.ClienteRepository;
import io.redspark.email.overview.repository.IntegranteRepository;
import io.redspark.email.overview.repository.ProjetoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/projeto")
public class ProjetoController {

	@Autowired
	private ProjetoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	@RequestMapping(method=GET)
	@Transactional(readOnly=true)
	public List<ProjetoDTO> list(){
		 List<Projeto> projetos = (List<Projeto>) repository.findAll();
		 
		 return projetos.stream()
			.map(ProjetoDTO::new)
			.collect(Collectors.toList());
	}
	
	@RequestMapping(value="/{id}", method=GET)
	@Transactional(readOnly=true)
	public ProjetoDTO findById(@PathVariable("id") Long id){
		Projeto projeto = repository.findOne(id);
		return new ProjetoDTO(projeto);
	}


	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public ProjetoDTO create(@RequestBody ProjetoForm form){
		Projeto projeto = new Projeto();
		projeto.setNome(form.getNome());
		projeto.setCliente(clienteRepository.findOne(form.getClienteId()));
		
		List<Integrante> integrantes = new ArrayList<Integrante>(); 
		for(Long id : form.getIntegrantes()){
			integrantes.add(integranteRepository.findOne(id));
		}
		projeto.setIntegrantes(integrantes);
		
		repository.save(projeto);
		
		return new ProjetoDTO(projeto);
	}

	
	@RequestMapping(value="/{id}", method=PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ProjetoDTO update(@PathVariable("id") Long id, @RequestBody ProjetoForm form){
		Projeto projeto = repository.findOne(id);
		projeto.setNome(form.getNome());
		projeto.setCliente(clienteRepository.findOne(form.getClienteId()));
		
		List<Integrante> integrantes = new ArrayList<Integrante>(); 
		for(Long integranteId : form.getIntegrantes()){
			integrantes.add(integranteRepository.findOne(integranteId));
		}
		projeto.setIntegrantes(integrantes);
		repository.save(projeto);
		
		return new ProjetoDTO(projeto);
	}
	
	@RequestMapping(value="/{id}", method=DELETE)
	@Transactional
	public ProjetoDTO delete(@PathVariable("id") Long id){
		Projeto projeto = repository.findOne(id);
		repository.delete(projeto);
		
		return new ProjetoDTO(projeto);
	}
	
}
