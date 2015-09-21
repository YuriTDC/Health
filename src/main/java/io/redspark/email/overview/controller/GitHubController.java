package io.redspark.email.overview.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.entity.Cliente;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.repository.RepositorioRepository;
import io.redspark.email.overview.service.EmailService;
import io.redspark.email.overview.service.GitHubService;
import io.redspark.email.overview.service.RepositorioService;

@RestController
@RequestMapping(value = "/github")
public class GitHubController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RepositorioRepository repositorioRepository;

	@Autowired
	private RepositorioService repositorioService;

	@Autowired
	private GitHubService gitHubService;

	@Autowired
	private EmailService emailService;

	private String url = "https://github.com/YuriTDC/Health";
	

	public final Logger log = Logger.getLogger(GitHubController.class);

	@RequestMapping(value = "/repos")
	public List<Repository> reposUser() throws IOException {

		List<Repository> repositories = repositoryService.getRepositories();

		return repositories;
	}

	@RequestMapping(value = "/email")
	public List<Issue> email() throws Exception {

		emailService.enviarEmail("gabriel.suaki@redspark.io", "hahahaha", "spottec", new HashSet<Integrante>());

		return gitHubService.getClosedIssuesSinceOntem(new Repository());
	}

	@RequestMapping(value = "/test-email")
	public String emailGit() throws Exception {

		log.info("Rodando Job");

		List<Repository> gitRepositories = gitHubService.getOrganizationRepositories("dclick");
		for (Repository gitRepository : gitRepositories) {

			Repositorio repositorio = repositorioRepository.findByRepositoryGitId(gitRepository.getId());

			repositorio = repositorioService.checkRemoteRepositoryWithLocalRepository(gitRepository, repositorio);

			if (repositorio.getNotificar() == true) {

				Integer bugsAtualmente = gitHubService.getOpenBugs(gitRepository).size();
				Integer backlogAtualmente = gitHubService.getOpenIssues(gitRepository).size();
				Integer inProgressAtualmente = gitHubService.getOpenIssuesInProgress(gitRepository).size();
				Integer closedAtualmente = gitHubService.getClosedIssues(gitRepository).size();
				closedAtualmente += gitHubService.getOpenIssuesResolved(gitRepository).size();

				Integer addedSinceOntem = gitHubService.getIssuesAddedSinceOntem(gitRepository).size();
				Integer bugsSinceOntem = gitHubService.getOpenBugsSinceOntem(gitRepository).size();
				Integer inProgressSinceOntem = gitHubService.getOpenIssuesInProgressSinceOntem(gitRepository).size();
				Integer closedSinceOntem = gitHubService.getClosedIssuesSinceOntem(gitRepository).size();

				List<Issue> tarefasFinalizadas = gitHubService.getClosedIssuesUltimosSeteDias(gitRepository);
				tarefasFinalizadas.addAll(gitHubService.getOpenIssuesResolvedUltimosSeteDias(gitRepository));

				Set<Integrante> time = repositorio.getTime();

				String texto = emailService.factoryEmail(gitRepository.getName(), bugsAtualmente, backlogAtualmente,
						inProgressAtualmente, closedAtualmente, addedSinceOntem, bugsSinceOntem, inProgressSinceOntem,
						closedSinceOntem, tarefasFinalizadas, time);

				for (Cliente cliente : repositorio.getClientes()) {

					emailService.enviarEmail(cliente.getEmail(), texto, gitRepository.getName(), time);
				}
			}
		}

		return "Rodou.";
	}

	@RequestMapping(value = "/enviar-email")
	public void validarEnvioEmail() throws Exception {
		emailService.enviarEmailSimples("gabriel.suaki@redspark.io", "olá");
	}

	@RequestMapping(value = "/status-repo")
	public void statusRepo() throws Exception {

		File path = File.createTempFile("TestGitRepository", "");
		path.delete();
		
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider("YuriTDC", "yuriTakassi123!");
		
		Git.cloneRepository().setURI(url).setCredentialsProvider(cp).setDirectory(path).call();

		Git git = Git.open(path);
		org.eclipse.jgit.lib.Repository repository = git.getRepository();
		
		Status status = new Git(repository).status().call();
		
		System.out.println("Adicionado: " + status.getAdded());
		System.out.println("Alterado: " + status.getChanged());
		System.out.println("Conflito: " + status.getConflicting());
		System.out.println("Faltando: " + status.getMissing());
		System.out.println("Modificado: " + status.getModified());
		System.out.println("Removido: " + status.getRemoved());

		repository.close();

	}

	@RequestMapping(value = "/backlog")
	public List<Issue> issuesEmBacklog() throws IOException {

		Repository repository = repositoryService.getRepository("dclick", "sesc-bertioga-web");

		return gitHubService.getOpenIssues(repository);
	}
	
}
