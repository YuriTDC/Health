package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.dto.DadosProjetoDTO;
import io.redspark.email.overview.entity.DadosProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.form.DadosProjetoForm;
import io.redspark.email.overview.repository.DadosProjetoRepository;
import io.redspark.email.overview.repository.RepositorioRepository;

@RestController
@RequestMapping(value = "/dadosProjeto")
public class DadosProjetoController {

	@Autowired
	private RepositorioRepository repositorioRepository;

	@Autowired
	private DadosProjetoRepository repository;

	@RequestMapping(value = "/repositorio/{repositorioId}", method = GET)
	@Transactional(readOnly = true)
	public List<DadosProjetoDTO> recuperarDadosProjeto(@PathVariable("repositorioId") Long repositorioId) {

		Repositorio repositorio = repositorioRepository.findOne(repositorioId);

		List<DadosProjeto> recuperaDados = (List<DadosProjeto>) repository.findByRepositorio(repositorio);

		return recuperaDados.stream().map(DadosProjetoDTO::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = GET)
	@Transactional(readOnly = true)
	public DadosProjetoDTO findById(@PathVariable("id") Long id) {
		DadosProjeto dados = repository.findOne(id);
		return new DadosProjetoDTO(dados);
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public DadosProjeto create(@RequestBody DadosProjetoForm form) {

		DadosProjeto dadosProjeto = new DadosProjeto();

		dadosProjeto.setRepositorio(repositorioRepository.findOne(form.getRepositorioId()));
		dadosProjeto.setDataInicio(form.getDataInicio());
		dadosProjeto.setDataTermimo(form.getDataTermino());
		dadosProjeto.setStatus(form.getStatus());

		dadosProjeto = repository.save(dadosProjeto);

		return dadosProjeto;
	}
	
	@Transactional
	@RequestMapping(value = "/{id}", method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DadosProjeto update(@PathVariable("id") Long id, @RequestBody DadosProjetoForm form) {
		
		DadosProjeto dadosProjeto = repository.findOne(id);
		
		dadosProjeto.setRepositorio(repositorioRepository.findOne(form.getRepositorioId()));
		dadosProjeto.setDataInicio(form.getDataInicio());
		dadosProjeto.setDataTermimo(form.getDataTermino());
		dadosProjeto.setStatus(form.getStatus());

		dadosProjeto = repository.save(dadosProjeto);

		return dadosProjeto;
	}

}
