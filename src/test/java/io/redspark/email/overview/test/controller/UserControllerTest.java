package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

import org.junit.Before;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerBase("/user")
public class UserControllerTest extends AbstractControllerTest {

	@SuppressWarnings("unused")
	private final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void before() throws Exception {
		user();
		saveAll();
		signIn();
	}
	
	public void recuperaUsuarioLogado() throws Exception{
		
		MockHttpServletRequestBuilder get = get();
		
		perform(get, status().isOk());
		
	}
	
}
