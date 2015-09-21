package io.redspark.email.overview.jobs;

import io.redspark.email.overview.entity.Cliente;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.repository.RepositorioRepository;
import io.redspark.email.overview.service.EmailService;
import io.redspark.email.overview.service.GitHubService;
import io.redspark.email.overview.service.RepositorioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailJob {
	
	private static final String SATURDAY = "SATURDAY";

	private static final String SUNDAY = "SUNDAY";

	private final Logger log = Logger.getLogger(EmailJob.class);
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private GitHubService gitHubService;
	
	@Autowired
	private RepositorioRepository repositorioRepository;
	
	@Autowired
	private RepositorioService repositorioService;
	
	@Autowired
	private Environment env;
	
	@Scheduled(cron = "00 00 10 * * *")
	public void enviarEmailsDiarios() throws Exception {
		
		if (LocalDate.now().getDayOfWeek().toString() != SUNDAY && LocalDate.now().getDayOfWeek().toString() != SATURDAY ) {
			log.info("Rodando email job.");
			
			List<Repository> gitRepositories = gitHubService.getOrganizationRepositories("dclick");
			
			for ( Repository gitRepository : gitRepositories ) {
				
				Repositorio repositorio = repositorioRepository.findByRepositoryGitId(gitRepository.getId());
				
				repositorio = repositorioService.checkRemoteRepositoryWithLocalRepository(gitRepository, repositorio);
				
				if (repositorio.getNotificar() == true) {
					
					Integer bugsAtualmente       	 = gitHubService.getOpenBugs(gitRepository).size();
					Integer backlogAtualmente    	 = gitHubService.getOpenIssues(gitRepository).size();
					Integer inProgressAtualmente 	 = gitHubService.getOpenIssuesInProgress(gitRepository).size();
					Integer closedAtualmente     	 = gitHubService.getClosedIssues(gitRepository).size();
					closedAtualmente 				+= gitHubService.getOpenIssuesResolved(gitRepository).size();
					 
					Integer addedSinceOntem 	 	 = gitHubService.getIssuesAddedSinceOntem(gitRepository).size();
					Integer bugsSinceOntem 	 		 = gitHubService.getOpenBugsSinceOntem(gitRepository).size();
					Integer inProgressSinceOntem 	 = gitHubService.getOpenIssuesInProgressSinceOntem(gitRepository).size();
					Integer closedSinceOntem 	 	 = gitHubService.getClosedIssuesSinceOntem(gitRepository).size();
					
					List<Issue> tarefasFinalizadas   = gitHubService.getClosedIssuesUltimosSeteDias(gitRepository);
					tarefasFinalizadas.addAll(gitHubService.getOpenIssuesResolvedUltimosSeteDias(gitRepository));
					
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
		}
	}
}
