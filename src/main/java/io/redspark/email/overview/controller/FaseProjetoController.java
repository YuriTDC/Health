package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.dto.FaseProjetoDTO;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.exceptions.EmailOverviewException;
import io.redspark.email.overview.form.FaseProjetoForm;
import io.redspark.email.overview.repository.FaseProjetoRepository;
import io.redspark.email.overview.repository.RepositorioRepository;

@RestController
@RequestMapping(value="/faseProjeto")
public class FaseProjetoController {
	
	@Autowired
	private RepositorioRepository repositorioRepository;
	
	@Autowired	
	private FaseProjetoRepository repository;
	
	@RequestMapping(method=GET)
	@Transactional(readOnly=true)
	public List<FaseProjetoDTO> list(){
		List<FaseProjeto> faseProjeto = (List<FaseProjeto>) repository.findAll();
		 return faseProjeto.stream()
				 .map(FaseProjetoDTO::new)
				 .collect(Collectors.toList());
	}
	
	@RequestMapping(value="/repositorio/{repositorioId}", method=GET)
	@Transactional(readOnly=true)
	public List<FaseProjetoDTO> recuperarFasesProjeto(@PathVariable("repositorioId") Long repositorioId ){
		
		Repositorio repositorio = repositorioRepository.findOne(repositorioId);
		
		List<FaseProjeto> recuperaFase = (List<FaseProjeto>) repository.findByRepositorio(repositorio);
		
		return recuperaFase.stream()
					.map(FaseProjetoDTO::new)
					.collect(Collectors.toList());
	}
	
	@RequestMapping(value="/{id}", method=GET)
	@Transactional(readOnly=true)
	public FaseProjetoDTO findById(@PathVariable("id") Long id){
		FaseProjeto faseProjeto = repository.findOne(id);
		return new FaseProjetoDTO(faseProjeto);
	}
	
	@Transactional
	@RequestMapping( method = RequestMethod.POST)
	public FaseProjeto create(@RequestBody FaseProjetoForm form){
		
		FaseProjeto faseProjeto = new FaseProjeto();
		faseProjeto.setRepositorio(repositorioRepository.findOne(form.getRepositorioId()));
		faseProjeto.setNome(form.getNome());
		faseProjeto.setStatus(form.getStatus());
		
		faseProjeto = repository.save(faseProjeto);
	
		return faseProjeto;
		
	}
	
	
	@Transactional
	@RequestMapping(value="/{id}", method=PUT, consumes={MediaType.APPLICATION_JSON_VALUE})
	public FaseProjeto update(@PathVariable("id") Long id, @RequestBody FaseProjetoForm form){
		
		FaseProjeto faseProjeto = repository.findOne(id);
		faseProjeto.setRepositorio(repositorioRepository.findOne(form.getRepositorioId()));
		faseProjeto.setNome(form.getNome());
		faseProjeto.setStatus(form.getStatus());
		
		faseProjeto = repository.save(faseProjeto);
		
		return faseProjeto;

	}
	
	@Transactional
	@RequestMapping(value="/{id}", method=DELETE)
	public List<FaseProjeto> delete(@PathVariable("id") Long id){
		
		List<FaseProjeto> findAll = new ArrayList<FaseProjeto>();
		try {
			
			repository.delete(id);
			findAll = (List<FaseProjeto>) repository.findAll();
		} catch (Exception e) {
			throw new EmailOverviewException(HttpStatus.PRECONDITION_FAILED, "Esta Fase está associado a um Sprint.");
		}
		
		
		return findAll;
	}

}
