package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.enums.StatusFase;
import io.redspark.email.overview.test.builders.FaseProjetoBuilder;
import io.redspark.email.overview.test.builders.RepositorioBuilder;
import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@ControllerBase("/faseProjeto")
public class FaseProjetoControllerTest extends AbstractControllerTest {

	private StatusFase status = StatusFase.EM_ANDAMENTO;

	private RepositorioBuilder repositorioBuilder;
	
	private RepositorioBuilder repositorioBuilder2;

	@Before
	public void before() throws Exception {
		user();
		repositorioBuilder = RepositorioBuilder.repositorio();
		repositorioBuilder2 = RepositorioBuilder.repositorio();
		
		saveAll();
		signIn();
	}

	@Test
	public void deveriaListarFasesRepositorio() throws Exception {
		
		Repositorio repositorio = repositorioBuilder.get();
		Repositorio repositorio2 = repositorioBuilder2.get();
		
		FaseProjetoBuilder.faseProjeto(repositorio).status(status).nome("a");
		FaseProjetoBuilder.faseProjeto(repositorio).status(status).nome("Teste2");
		FaseProjetoBuilder.faseProjeto(repositorio).status(status).nome("Teste3");
		FaseProjetoBuilder.faseProjeto(repositorio2).status(status).nome("Teste1");
		saveAll();
		
		MockHttpServletRequestBuilder get = get("/repositorio/{repositorioId}", repositorio.getId());
		
		MvcResult mvcResult = perform(get, status().isOk());

		jsonAssert(mvcResult)
			.assertEquals("$.[0].nome", equalTo("a"))
			.assertEquals("$.[0].status", status.toString())
			.assertEquals("$.[0].repositorioId", repositorio.getId().intValue())
			.assertThat("$", hasSize(3));
	}

	@Test
	public void deveriaBuscarPorId() throws Exception {

		Repositorio repositorio = repositorioBuilder.get();

		FaseProjetoBuilder faseProjetoBuilder = FaseProjetoBuilder.faseProjeto(
				repositorio).status(status);
		saveAll();

		MockHttpServletRequestBuilder mock = get("/{id}",
				faseProjetoBuilder.getId());

		MvcResult mvcResult = perform(mock, status().isOk());

		jsonAssert(mvcResult)
				.assertThat("$.id",
						equalTo(faseProjetoBuilder.getId().intValue()))
				.assertThat("$.repositorioId",
						equalTo(repositorio.getId().intValue()))
				.assertThat("$.status", equalTo(status.name()));

	}

	@Test
	public void deveriaCriarUmaFase() throws Exception {

		RepositorioBuilder repoBuilder = RepositorioBuilder.repositorio();
		saveAll();
		Repositorio repositorio = repoBuilder.get();

		FaseProjetoBuilder faseProjetoBuilder = FaseProjetoBuilder.faseProjeto(repositorio).status(status);
		saveAll();
		
		FaseProjeto fp = faseProjetoBuilder.get();
		
		MockHttpServletRequestBuilder mock = post().contentType(MediaType.APPLICATION_JSON);
		mock.param("repositorioId", repositorio.getId().toString());
		//mock.param("status", status.name());

		MvcResult mvcResult = perform(mock, status().isCreated());

		jsonAssert(mvcResult)
		.assertThat("$.repositorioId", equalTo(repositorio.getId().intValue()));			

	}

	@Test
	public void deveriaAtualizar() throws Exception {

		Repositorio repositorio = repositorioBuilder.get();

		FaseProjetoBuilder faseProjetoBuilder = FaseProjetoBuilder.faseProjeto(
				repositorio).status(status);
		saveAll();

		MockHttpServletRequestBuilder mock = put("/{id}",
				faseProjetoBuilder.getId());
		mock.param("status", StatusFase.ENTREGUE.toString());

		MvcResult mvcResult = perform(mock, status().isOk());
		
		jsonAssert(mvcResult)
		.assertThat("$.status",equalTo(StatusFase.ENTREGUE.toString()))
		.assertThat("$.repositorioId", equalTo(repositorio.getId().intValue()));
	}

	@Test
	public void deveriaDeletar() throws Exception {

		Repositorio repositorio = repositorioBuilder.get();

		FaseProjetoBuilder faseProjetoBuilder = FaseProjetoBuilder.faseProjeto(
				repositorio).status(status);
		saveAll();

		MockHttpServletRequestBuilder mock = delete("/{id}",
				faseProjetoBuilder.getId());

		MvcResult mvcResult = perform(mock, status().isOk());

		jsonAssert(mvcResult)
				.assertThat("$.id",
						equalTo(faseProjetoBuilder.getId().intValue()))
				.assertThat("$.repositorioId",
						equalTo(repositorio.getId().intValue()))
				.assertThat("$.status", equalTo(status.name()));
	}
}
