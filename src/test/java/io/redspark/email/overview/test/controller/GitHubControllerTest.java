package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

@ControllerBase("/github")
public class GitHubControllerTest extends AbstractControllerTest {

	@Autowired
	private RepositoryService repositoryService;
	
	@SuppressWarnings("unused")
	private final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void before() throws Exception {
		user();
		saveAll();
		signIn();
	}

	@Test
	public void deveriaRetornarOpenIssuesDiferentesDeResolved() throws Exception {

		perform(get("/enviar-email"), status().isOk());
	}

	@Test
	public void deveriaRetornarStatusRepo() throws Exception {

		
		perform(get("/status-repo"), status().isOk());

	}
}
