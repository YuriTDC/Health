package io.redspark.email.overview.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.entity.Cliente;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.form.RepositorioClienteForm;
import io.redspark.email.overview.form.RepositorioIntegranteForm;
import io.redspark.email.overview.repository.ClienteRepository;
import io.redspark.email.overview.repository.IntegranteRepository;
import io.redspark.email.overview.repository.RepositorioRepository;
import io.redspark.email.overview.service.EmailService;
import io.redspark.email.overview.service.GitHubService;

@RestController
@RequestMapping(value = "/repositorio")
public class RepositorioController {

	@Autowired
	private RepositorioRepository repositorioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private IntegranteRepository integranteRepository;
	
	@Autowired
	private GitHubService gitHubService;
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger log = LoggerFactory.getLogger(RepositorioController.class);
	
	
	
	@Transactional
	@RequestMapping(method = RequestMethod.GET)
	public List<Repositorio> listar() {
		
		List<Repositorio> all = (List<Repositorio>) repositorioRepository.findAll();
		Collections.sort(all, ComparatorPorNome);
		return all;
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Repositorio recuperar(@PathVariable("id") Long id) {
		return repositorioRepository.findOne(id);
	}

	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{repositorioId}/clientes", method = RequestMethod.GET)
	public List<Cliente> recuperarCliente(
			@PathVariable("repositorioId") Long repositorioId) {

		Repositorio repositorio = repositorioRepository.findOne(repositorioId);
		
		Set<Cliente> clientes = repositorio.getClientes();
		
		List list = new ArrayList(clientes);
		
		Collections.sort(list, ComparatorClienePorNome);
		
		return list;
	}

	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{repositorioId}/integrantes", method = RequestMethod.GET)
	public List<Integrante> recuperarIntegrantes(
			@PathVariable("repositorioId") Long repositorioId) {

		Repositorio repositorio = repositorioRepository.findOne(repositorioId);

		Set<Integrante> time = repositorio.getTime();
		
		List list = new ArrayList(time);
		
		Collections.sort(list, ComparatorIntegrantePorNome);
		
		return list;
	}

	@Transactional
	@RequestMapping(value = "/{repositorioId}/clientes", method = RequestMethod.PUT)
	public Set<Cliente> editarCliente(@PathVariable("repositorioId") Long repositorioId, @RequestBody RepositorioClienteForm form) {

		Repositorio repositorio = repositorioRepository.findOne(repositorioId);

		Set<Cliente> clientes = repositorio.getClientes();
		clientes.clear();

		for (Long id : form.getClientes()) {

			Cliente c = clienteRepository.findOne(id);
			clientes.add(c);
		}

		repositorio.setClientes(clientes);

		repositorioRepository.save(repositorio);

		return repositorio.getClientes();
	}

	@Transactional
	@RequestMapping(value = "/{repositorioId}/integrantes", method = RequestMethod.PUT)
	public Set<Integrante> editarIntegrantes(@PathVariable("repositorioId") Long repositorioId, @RequestBody RepositorioIntegranteForm form) {

		Repositorio repositorio = repositorioRepository.findOne(repositorioId);

		Set<Integrante> time = repositorio.getTime();
		time.clear();

		for (Long id : form.getTime()) {

			Integrante c = integranteRepository.findOne(id);
			time.add(c);
		}

		repositorio.setTime(time);

		repositorioRepository.save(repositorio);

		return repositorio.getTime();
	}
	
	@Transactional
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public List<Repositorio> atualizarRepositorios() {
		
		List<Repository> gitRepositories = gitHubService.getOrganizationRepositories("dclick");
		
		for ( Repository gitRepository : gitRepositories ) {
			Repositorio repositorio = repositorioRepository.findByRepositoryGitId(gitRepository.getId());
			
			if (repositorio == null ) {
				
				repositorio = new Repositorio();
				repositorio.setNome(gitRepository.getName());
				repositorio.setRepositoryGitId(gitRepository.getId());
				
				repositorioRepository.save(repositorio);
			}
		}
		
		List<Repositorio> all = (List<Repositorio>) repositorioRepository.findAll();
		
		Collections.sort(all, ComparatorPorNome);
		
		return all;
	}
	
	@Transactional
	@RequestMapping(value = "/{id}/notificar", method = RequestMethod.POST)
	public Repositorio setNotificar(@PathVariable("id") Long id, @RequestParam("notificar") boolean notificar) {
		
		Repositorio repositorio = repositorioRepository.findOne(id);
		
		repositorio.setNotificar(notificar);
		
		repositorio = repositorioRepository.save(repositorio);
		
		return repositorio;
	}

	@Transactional
	@RequestMapping(value = "/enviar", method = RequestMethod.GET)
	public String enviarEmails() throws Exception {
		
		log.info("Enviando emails.");
		
		List<Repository> gitRepositories = gitHubService.getOrganizationRepositories("dclick");
		
		for ( Repository gitRepository : gitRepositories ) {
			
			Repositorio repositorio = repositorioRepository.findByRepositoryGitId(gitRepository.getId());
			
			if (repositorio.getNotificar() == true) {
				
				Integer bugsAtualmente       	 = gitHubService.getOpenBugs(gitRepository).size();
				Integer backlogAtualmente    	 = gitHubService.getOpenIssues(gitRepository).size();
				Integer inProgressAtualmente 	 = gitHubService.getOpenIssuesInProgress(gitRepository).size();
				Integer closedAtualmente     	 = gitHubService.getClosedIssues(gitRepository).size();
				 
				Integer addedSinceOntem 	 	 = gitHubService.getIssuesAddedSinceOntem(gitRepository).size();
				Integer bugsSinceOntem 	 		 = gitHubService.getOpenBugsSinceOntem(gitRepository).size();
				Integer inProgressSinceOntem 	 = gitHubService.getOpenIssuesInProgressSinceOntem(gitRepository).size();
				Integer closedSinceOntem 	 	 = gitHubService.getClosedIssuesSinceOntem(gitRepository).size();
				
				List<Issue> tarefasFinalizadas   = gitHubService.getClosedIssuesUltimosSeteDias(gitRepository);
				
				Set<Integrante> time 			 = repositorio.getTime();
				
				String texto = emailService.factoryEmail(gitRepository.getName() , bugsAtualmente, backlogAtualmente, inProgressAtualmente, closedAtualmente, addedSinceOntem, bugsSinceOntem, inProgressSinceOntem, closedSinceOntem, tarefasFinalizadas, time);
				
				for ( Cliente cliente : repositorio.getClientes() ) {
					
					emailService.enviarEmail(cliente.getEmail(), texto, gitRepository.getName(), time);
				}
				
				for ( Integrante integrante : repositorio.getTime() ) {
					
					emailService.enviarEmail(integrante.getEmail(), texto, gitRepository.getName(), time);
				}
			}
			
		}
		
		return "Enviado com sucesso";
	}
	
	
	public static Comparator<Repositorio> ComparatorPorNome = new Comparator<Repositorio>() {
		public int compare(Repositorio repo, Repositorio repo2) {
			return repo.getNome().toUpperCase().compareTo(repo2.getNome().toUpperCase());
		}
	};
	
	public static Comparator<Integrante> ComparatorIntegrantePorNome = new Comparator<Integrante>() {
		public int compare(Integrante inte, Integrante inte2) {
			return inte2.getNome().toUpperCase().compareTo(inte2.getNome().toUpperCase());
		}
	};
	
	public static Comparator<Cliente> ComparatorClienePorNome = new Comparator<Cliente>() {
		public int compare(Cliente cli, Cliente cli2) {
			return cli.getNome().toUpperCase().compareTo(cli2.getNome().toUpperCase());
		}
	};

}
