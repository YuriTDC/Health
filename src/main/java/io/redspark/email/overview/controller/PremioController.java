package io.redspark.email.overview.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import io.redspark.email.overview.dto.PremioDTO;
import io.redspark.email.overview.entity.Premio;
import io.redspark.email.overview.form.PremioForm;
import io.redspark.email.overview.repository.PremioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/premio")
public class PremioController {

	@Autowired
	private PremioRepository repository;

	@RequestMapping(method=GET)
	@Transactional(readOnly=true)
	public List<PremioDTO> list(){
		 List<Premio> premios = (List<Premio>) repository.findAll();
		 
		 return premios.stream()
				.map(PremioDTO::new)
				.collect(Collectors.toList());
	}

	@RequestMapping(value="/{id}", method=GET)
	@Transactional(readOnly=true)
	public PremioDTO findById(@PathVariable("id") Long id){
		Premio premio = repository.findOne(id);
		return new PremioDTO(premio);
	}
	
	@RequestMapping(method=POST)
	//@ResponseStatus(value=HttpStatus.CREATED)
	@Transactional
	public Premio create(@RequestBody PremioForm form){
		Premio premio = new Premio();
		premio.setNome(form.getNome());
		premio.setValor(form.getValor());
		
		premio = repository.save(premio);
		return premio;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public Premio update(@PathVariable("id") Long id, @RequestBody PremioForm form){
		Premio premio = repository.findOne(id);
		premio.setNome(form.getNome());
		premio.setValor(form.getValor());
		
		premio = repository.save(premio);
		
		return premio;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Transactional
	public List<Premio> delete(@PathVariable("id") Long id) {
		List<Premio> findAll = new ArrayList<Premio>();
		repository.delete(id);
		findAll = (List<Premio>) repository.findAll();

		return findAll;
	}
}
