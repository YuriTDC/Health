package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.form.EntregaSprintForm;
import io.redspark.email.overview.test.builders.EntregaSprintBuilder;
import io.redspark.email.overview.test.builders.FaseProjetoBuilder;
import io.redspark.email.overview.test.builders.IntegranteBuilder;
import io.redspark.email.overview.test.builders.RepositorioBuilder;
import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerBase("/entregaSprint")
public class EntregaSprintControllerTest extends AbstractControllerTest{
	
	private final ObjectMapper mapper = new ObjectMapper();

	private FaseProjeto faseProjeto;
	
	private Repositorio repositorio;
	
	
	@Before
	public void before() throws Exception {
		IntegranteBuilder joao = IntegranteBuilder.integrante();
		IntegranteBuilder felipe = IntegranteBuilder.integrante().nome("Felipe");
		IntegranteBuilder gustavo = IntegranteBuilder.integrante().nome("Gustavo");
		user();
		saveAll();
		signIn();
		
		RepositorioBuilder repositorioBuilder = RepositorioBuilder.repositorio().time(joao.get(), felipe.get(), gustavo.get());
		saveAll();
		repositorio = repositorioBuilder.get();
		FaseProjetoBuilder faseProjetoBuilder = FaseProjetoBuilder.faseProjeto(repositorio);
		saveAll();
		
		faseProjeto = faseProjetoBuilder.get();
	
	}
	
	@Test
	public void deveriaListar() throws Exception{
		
		EntregaSprintBuilder entregaSprint = EntregaSprintBuilder.entregaSprint(faseProjeto);
		saveAll();
		
		MockHttpServletRequestBuilder mock = get("/{id}",
				entregaSprint.getId());

		MvcResult mvcResult = perform(mock, status().isOk());

		jsonAssert(mvcResult)
			.assertEquals("$.id", entregaSprint.getId().intValue())
			.assertEquals("$.faseId", faseProjeto.getId().intValue())
			.assertEquals("$.entregue", false);
	}
	
	@Test
	public void deveriaBuscarPorId() throws Exception {
		
		EntregaSprintBuilder entregaBuilder = EntregaSprintBuilder.entregaSprint(faseProjeto).entregue(false);
		saveAll();

		MockHttpServletRequestBuilder mock = get("/{id}", entregaBuilder.getId());
		MvcResult mvcResult = perform(mock, status().isOk());
		jsonAssert(mvcResult)
				.assertThat("$.id", equalTo(entregaBuilder.getId().intValue()))
				.assertThat("$.faseId", equalTo(faseProjeto.getId().intValue()))
				.assertThat("$.entregue", equalTo(false));
	}
	
	@Test
	public void deveriaBuscarPorFase() throws Exception {	
		IntegranteBuilder joao = IntegranteBuilder.integrante();
		IntegranteBuilder felipe = IntegranteBuilder.integrante().nome("Felipe");
		IntegranteBuilder gustavo = IntegranteBuilder.integrante().nome("Gustavo");
		saveAll();
		
		RepositorioBuilder repositorioBuilder = RepositorioBuilder.repositorio().time(joao.get(), felipe.get(), gustavo.get());
		saveAll();
		Repositorio repositorioTeste = repositorioBuilder.get();
		saveAll();
		
		FaseProjetoBuilder fase1 = FaseProjetoBuilder.faseProjeto(repositorioTeste);
		FaseProjetoBuilder fase2 = FaseProjetoBuilder.faseProjeto(repositorioTeste);
		saveAll();
		
		FaseProjeto faseTeste = fase1.get();
		FaseProjeto faseTeste2 = fase2.get();
		saveAll();

		EntregaSprintBuilder entregaSprint = EntregaSprintBuilder.entregaSprint(faseTeste);
		EntregaSprintBuilder entregaSprint2 = EntregaSprintBuilder.entregaSprint(faseTeste);
		saveAll();

		EntregaSprintBuilder.entregaSprint(faseTeste2);
		EntregaSprintBuilder.entregaSprint(faseTeste2);

		saveAll();
		
		MockHttpServletRequestBuilder get = get("/fase/{faseId}", faseTeste.getId());
		MvcResult mvcResult = perform(get, status().isOk());
		jsonAssert(mvcResult)
		.assertThat("$", hasSize(2))
		.assertThat("$.[*].id", contains(entregaSprint.getId().intValue(), entregaSprint2.getId().intValue()));
	}
		
	
	@Test
	public void deveriaCriar() throws Exception{
		EntregaSprintForm form = new EntregaSprintForm();
		form.setFaseId(faseProjeto.getId());
		form.setEntregue(false);
		
		MockHttpServletRequestBuilder mock = post().content(mapper.writeValueAsBytes(form)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = perform(mock, status().isCreated());
		jsonAssert(mvcResult)
			.assertThat("$.faseId", equalTo(faseProjeto.getId().intValue()))
			.assertThat("$.time", hasSize(3));
		
	}
	
	@Test
	public void deveriaAtualizar() throws Exception{
		
		EntregaSprintBuilder entregaSprintBuilder = EntregaSprintBuilder.entregaSprint(faseProjeto).entregue(false);
		saveAll();
		
		EntregaSprintForm form = new EntregaSprintForm();
		form.setFaseId(faseProjeto.getId());
		form.setEntregue(true);
		
		MockHttpServletRequestBuilder mock = put("/{id}", entregaSprintBuilder.getId()).content(mapper.writeValueAsBytes(form)).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = perform(mock, status().isOk());
		
		jsonAssert(mvcResult)
			.assertThat("$.faseId", equalTo(faseProjeto.getId().intValue()))
			.assertThat("$.entregue", equalTo(true));
	}
	
	@Test
	public void deveriaDeletar() throws Exception{
		
		EntregaSprintBuilder entregaSprintBuilder = EntregaSprintBuilder.entregaSprint(faseProjeto).entregue(false);
		saveAll();
		
		MockHttpServletRequestBuilder mock = delete("/{id}", entregaSprintBuilder.getId());
		
		MvcResult mvcResult = perform(mock, status().isOk());
		
		jsonAssert(mvcResult)
			.assertThat("$.id", equalTo(entregaSprintBuilder.getId().intValue()))
			.assertThat("$.faseId", equalTo(faseProjeto.getId().intValue()))
			.assertThat("$.entregue", equalTo(false));
	}
	
	
}



