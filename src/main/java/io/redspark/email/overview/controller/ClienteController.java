package io.redspark.email.overview.controller;

import io.redspark.email.overview.entity.Cliente;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.exceptions.EmailOverviewException;
import io.redspark.email.overview.form.ClienteForm;
import io.redspark.email.overview.repository.ClienteRepository;
import io.redspark.email.overview.repository.RepositorioRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private RepositorioRepository repositorioRepository;

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public Cliente create(@RequestBody ClienteForm form) {

		Cliente c = new Cliente();

		c.setNome(form.getNome());
		c.setEmail(form.getEmail());

		c = repository.save(c);

		return c;
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Cliente edit(@PathVariable("id") Long id, @RequestBody ClienteForm form) {

		Cliente c = repository.findOne(id);

		c.setNome(form.getNome());
		c.setEmail(form.getEmail());

		c = repository.save(c);

		return c;
	}

	@Transactional
	@RequestMapping(method = RequestMethod.GET)
	public List<Cliente> listar() {
		return (List<Cliente>) repository.findAll();
	}

	@Transactional
	@RequestMapping(value = "/others/{repositorioId}", method = RequestMethod.GET)
	public List<Cliente> listarOthers(@PathVariable("repositorioId") Long repositorioId) {
		
		List<Cliente> clientes = new ArrayList<>();
		List<Cliente> findAll = (List<Cliente>) repository.findAll();
		
		Repositorio repo = repositorioRepository.findOne(repositorioId);
		boolean alreadyAdded = false;
		
		for ( Cliente cliente : findAll ) {
			
			alreadyAdded = false;
			
			for ( Cliente c : repo.getClientes() ) {
				
				if (cliente.getId() == c.getId() ) {
					alreadyAdded = true;
					break;
				}
			}
			
			if( alreadyAdded == false) {
				clientes.add(cliente);
			}
		}
		
		Collections.sort(clientes, ComparatorPorNome);
		
		return clientes;
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Cliente recuperar(@PathVariable("id") Long id) {
		return repository.findOne(id);
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public List<Cliente> excluir(@PathVariable("id") Long id) {
		
		List<Cliente> findAll = new ArrayList<Cliente>();
		
		try {
			
			repository.delete(id);
			findAll = (List<Cliente>) repository.findAll();
		} catch (Exception e) {
			throw new EmailOverviewException(HttpStatus.PRECONDITION_FAILED, "Este cliente está associado a um repositório.");
		}
		
		
		return findAll;
	}
	
	public static Comparator<Cliente> ComparatorPorNome = new Comparator<Cliente>() {
		public int compare(Cliente cli, Cliente cli2) {
			return cli.getNome().toUpperCase().compareTo(cli2.getNome().toUpperCase());
		}
	};
}
