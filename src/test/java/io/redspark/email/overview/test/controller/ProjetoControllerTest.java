package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.redspark.email.overview.entity.Cliente;
import io.redspark.email.overview.form.ProjetoForm;
import io.redspark.email.overview.test.builders.ClienteBuilder;
import io.redspark.email.overview.test.builders.IntegranteBuilder;
import io.redspark.email.overview.test.builders.ProjetoBuilder;
import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerBase("/projeto")
public class ProjetoControllerTest extends AbstractControllerTest  {

	private final ObjectMapper mapper = new ObjectMapper();

	private Cliente cliente;
	
	@Before
	public void before() throws Exception {
		user();
		saveAll();
		signIn();
		
		ClienteBuilder clienteBuilder = ClienteBuilder.cliente().nome("redspark");
		saveAll();
		cliente = clienteBuilder.get();
	}
	
	@Test
	public void testList() throws Exception{
		ProjetoBuilder.projeto(cliente).nome("projeto 2");
		ProjetoBuilder.projeto(cliente).nome("projeto 1");
		ProjetoBuilder.projeto(cliente).nome("projeto 3");
		ProjetoBuilder.projeto(cliente).nome("projeto 4");
		saveAll();
		
		MockHttpServletRequestBuilder mock = get();
		
		MvcResult mvcResult = perform(mock, status().isOk());
		
		jsonAssert(mvcResult).assertThat("$", hasSize(4));
	}
	
	@Test
	public void testFindById() throws Exception{
		ProjetoBuilder projetoBuilder = ProjetoBuilder.projeto(cliente).nome("projeto");
		saveAll();
		
		MockHttpServletRequestBuilder mock = get("/{id}", projetoBuilder.getId());
		MvcResult mvcResult = perform(mock, status().isOk());
		jsonAssert(mvcResult)
		.assertThat("$.id", equalTo(projetoBuilder.getId().intValue()))
		.assertThat("$.nome", equalTo("projeto"))
		.assertThat("$.clienteId", equalTo(cliente.getId().intValue()))
		.assertThat("$.clienteNome", equalTo("redspark"));
	}
	
	@Test
	public void testCreate() throws Exception{
		IntegranteBuilder joao = IntegranteBuilder.integrante();
		IntegranteBuilder felipe = IntegranteBuilder.integrante().nome("Felipe");
		IntegranteBuilder marcela = IntegranteBuilder.integrante().nome("Marcela");
		saveAll();
		
		ProjetoForm form = new ProjetoForm();
		form.setNome("Projeto");
		form.setClienteId(cliente.getId());
		form.setIntegrantes(Arrays.asList(joao.getId(), felipe.getId(), marcela.getId()));
		
		MockHttpServletRequestBuilder mock = post().content(mapper.writeValueAsBytes(form)).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = perform(mock, status().isCreated());
		jsonAssert(mvcResult)
		.assertThat("$.nome", equalTo("Projeto"))
		.assertThat("$.clienteId", equalTo(cliente.getId().intValue()))
		.assertThat("$.clienteNome", equalTo("redspark"));
		
		
	}
	
	@Test
	public void testUpdate() throws Exception{
		ClienteBuilder dclickBuilder = ClienteBuilder.cliente().nome("dclick");
		IntegranteBuilder joao = IntegranteBuilder.integrante();
		IntegranteBuilder felipe = IntegranteBuilder.integrante().nome("Felipe");
		IntegranteBuilder marcela = IntegranteBuilder.integrante().nome("Marcela");
		saveAll();
		ProjetoBuilder projetoBuilder = ProjetoBuilder.projeto(cliente).nome("projeto");
		saveAll();
		
		ProjetoForm form = new ProjetoForm();
		form.setNome("Sesc");
		form.setClienteId(dclickBuilder.getId());
		form.setIntegrantes(Arrays.asList(joao.getId(), felipe.getId(), marcela.getId()));
		
		MockHttpServletRequestBuilder mock = put("/{id}", projetoBuilder.getId()).content(mapper.writeValueAsBytes(form)).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = perform(mock, status().isOk());
		jsonAssert(mvcResult)
		.assertThat("$.nome", equalTo("Sesc"))
		.assertThat("$.clienteId", equalTo(dclickBuilder.getId().intValue()))
		.assertThat("$.clienteNome", equalTo("dclick"));
	}
	
	@Test
	public void testDelete() throws Exception{
		ProjetoBuilder projetoBuilder = ProjetoBuilder.projeto(cliente).nome("projeto");
		saveAll();
		
		MockHttpServletRequestBuilder mock = delete("/{id}", projetoBuilder.getId());
		MvcResult mvcResult = perform(mock, status().isOk());
		jsonAssert(mvcResult)
		.assertThat("$.nome", equalTo("projeto"))
		.assertThat("$.clienteId", equalTo(cliente.getId().intValue()))
		.assertThat("$.clienteNome", equalTo("redspark"));
	}
	
}
