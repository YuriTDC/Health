package io.redspark.email.overview.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.service.WatcherService;
import org.jboss.logging.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitHubService {
	
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private MilestoneService milestoneService;

	@Autowired
	private IssueService issueService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private WatcherService watcherService;
	
	@Autowired
	private GitHubClient client;
	
	private final Logger log = Logger.getLogger(GitHubService.class);
	
	private final String DIA_DE_ONTEM = LocalDateTime.now().minusDays(1).toString();
	
	private final String SETE_DIAS_ATRAS = LocalDateTime.now().minusDays(7).toString();
	
	public List<Issue> getOpenBugs(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "bug");
		
		List<Issue> issues = new ArrayList<>();
		List<Issue> filtredIssues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
			
			for (Issue i : issues) {
				
				int lastIndex = i.getLabels().size() - 1;
				
				if (!i.getLabels().isEmpty()) {
					
					if (!i.getLabels().get(lastIndex).getName().equals("resolved")) {
						filtredIssues.add(i);
					}
				} else {
					
					filtredIssues.add(i);
				}
			}
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar os atuais bugs.");
		}
		
		return filtredIssues;
	}

	public List<Issue> getOpenIssuesInProgress(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "in progress");
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues in progress.");
		}
		
		return issues;
	}

	public List<Issue> getOpenIssues(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		
		List<Issue> issues = new ArrayList<>();
		List<Issue> filtredIssues = new ArrayList<>();
		
		try {
			
			issues = issueService.getIssues(gitRepository, map);
			
			for (Issue i : issues) {
				
				int lastIndex = i.getLabels().size() - 1;
				
				if (!i.getLabels().isEmpty()) {
					
					if (!i.getLabels().get(lastIndex).getName().equals("resolved")) {
						filtredIssues.add(i);
					}
				} else {
					
					filtredIssues.add(i);
				}
			}
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues abertas.");
		}
		
		return filtredIssues;
	}

	public List<Issue> getClosedIssues(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "closed");
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues fechadas.");
		}
		
		return issues;
	}

	public List<Issue> getOpenIssuesResolved(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "resolved");
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues in resolved.");
		}
		
		return issues;
	}
	
	public List<Issue> getIssuesAddedSinceOntem(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "all");
		map.put("since", DIA_DE_ONTEM);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar os atuais bugs.");
		}
		
		return issues;
	}
	
	public List<Issue> getOpenBugsSinceOntem(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "bug");
		map.put("sort", "updated");
		map.put("since", DIA_DE_ONTEM);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar os atuais bugs.");
		}
		
		return issues;
	}
	
	public List<Issue> getOpenIssuesInProgressSinceOntem(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "in progress");
		map.put("since", DIA_DE_ONTEM);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues in progress.");
		}
		
		return issues;
	}
	
	public List<Issue> getOpenIssuesInBacklogSinceOntem(String repositoryName) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "backlog");
		map.put("since", DIA_DE_ONTEM);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(issueService.getClient().getUser(), repositoryName, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues in backlog.");
		}
		
		return issues;
	}
	
	public List<Issue> getClosedIssuesSinceOntem(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "closed");
		map.put("since", DIA_DE_ONTEM);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues fechadas.");
		}
		
		return issues;
	}

	public List<Issue> getOpenIssuesResolvedSinceOntem(String repositoryName) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "resolved");
		map.put("since", DIA_DE_ONTEM);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(issueService.getClient().getUser(), repositoryName, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues in resolved.");
		}
		
		return issues;
	}

	public List<Issue> getClosedIssuesUltimosSeteDias(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "closed");
		map.put("since", SETE_DIAS_ATRAS);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues fechadas.");
		}
		
		return issues;
	}

	public List<Issue> getOpenIssuesResolvedUltimosSeteDias(Repository gitRepository) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "open");
		map.put("labels", "resolved");
		map.put("since", SETE_DIAS_ATRAS);
		
		List<Issue> issues = new ArrayList<>();
		
		try {
			issues = issueService.getIssues(gitRepository, map);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar as atuais issues in resolved.");
		}
		
		return issues;
	}
	
	public List<Repository> getRepositories() {
		
		List<Repository> repositories = new ArrayList<>();
		
		try {
			repositories = repositoryService.getRepositories();
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar os atuais repositorios.");
		}
		
		return repositories;
	}

	public List<Repository> getOrganizationRepositories(String org) {
		
		List<Repository> repositories = new ArrayList<>();
		
		try {
			repositories = repositoryService.getOrgRepositories(org);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar os atuais repositorios da organização.");
		}
		
		return repositories;
	}
	
	public List<Contributor> getContribuitors(Repository repositorio) {
	
		List<Contributor> contribuidores = new ArrayList<>();
		
		try {
			contribuidores = repositoryService.getContributors(repositorio, false);
		} catch (IOException e) {
			log.error("GitHubService - Erro ao tentar recuperar os contribuidores do repositorio.");
		}
		
		return contribuidores;
	}
	
	public List<Issue> getIssuesOfAMilestone(Repository repositorio) throws IOException {
		
		String state = "all";

		List<Milestone> milestones = milestoneService.getMilestones(repositorio, state);
		Collection<Date> datas = new ArrayList<>();
		
		for(Milestone mi : milestones) {
			datas.add(mi.getDueOn());
		}

		
		Date proximo = closerDate(new Date(), datas);
		Integer number = null;
		
		for(Milestone mi : milestones) {
			if(mi.getDueOn().equals(proximo)) {
				number = mi.getNumber();
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "closed");
		map.put("milestone", number.toString());
		
		List<Issue> issues = issueService.getIssues(repositorio, map);
		
		return issues;
	}
	
	public static Date closerDate(Date originalDate, Collection<Date> unsortedDates) {
	    List<Date> dateList = new LinkedList<Date>(unsortedDates);
	    Collections.sort(dateList);
	    Iterator<Date> iterator = dateList.iterator();
	    Date previousDate = null;
	    while (iterator.hasNext()) {
	        Date nextDate = iterator.next();
	        if (nextDate.before(originalDate)) {
	            previousDate = nextDate;
	            continue;
	        } else if (nextDate.after(originalDate)) {
	            if (previousDate == null || isCloserToNextDate(originalDate, previousDate, nextDate)) {
	                return nextDate;
	            }
	        } else {
	            return nextDate;
	        }
	    }
	    return previousDate;
	}
	
	private static boolean isCloserToNextDate(Date originalDate, Date previousDate, Date nextDate) {
	    if(previousDate.after(nextDate))
	        throw new IllegalArgumentException("previousDate > nextDate");
	    return ((nextDate.getTime() - previousDate.getTime()) / 2 + previousDate.getTime() <= originalDate.getTime());
	}
}
