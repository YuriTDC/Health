package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Premio;
import io.redspark.email.overview.form.TrocaPremioForm;
import io.redspark.email.overview.test.builders.IntegranteBuilder;
import io.redspark.email.overview.test.builders.PremioBuilder;
import io.redspark.email.overview.test.builders.TrocaPremioBuilder;
import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerBase("/trocaPremio")
public class TrocaPremioControllerTest extends AbstractControllerTest {

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void before() throws Exception {

		user();
		saveAll();
		signIn();

	}

	@Test
	public void testList() throws Exception {

		IntegranteBuilder integrante = IntegranteBuilder.integrante();
		PremioBuilder premio = PremioBuilder.premio();
		saveAll();
		
		TrocaPremioBuilder.trocaPremio(integrante.get(), premio.get());
		saveAll();
	
		MockHttpServletRequestBuilder mock = get();

		MvcResult mvcResult = perform(mock, status().isOk());

		jsonAssert(mvcResult).assertThat("$", hasSize(1));
	}

	@Test
	public void testFindById() throws Exception{
		
		IntegranteBuilder integrante = IntegranteBuilder.integrante();
		PremioBuilder premio = PremioBuilder.premio();
		saveAll();
		
		TrocaPremioBuilder trocaPremio = TrocaPremioBuilder.trocaPremio(integrante.get(), premio.get());
		saveAll();
		
		MockHttpServletRequestBuilder mock = get("/{id}", trocaPremio.getId());
		MvcResult mvcResult = perform(mock, status().isOk());
		jsonAssert(mvcResult)
		.assertThat("$.id", equalTo(trocaPremio.getId().intValue()))
		.assertThat("$.integranteId", equalTo(integrante.getId().intValue()))
		.assertThat("$.premioId", equalTo(premio.getId().intValue()));
	}
	
	@Test
	public void testlistByIntegrante() throws Exception{
		PremioBuilder premio1 = PremioBuilder.premio().nome("teste");		
		PremioBuilder premio2 = PremioBuilder.premio().nome("teste3");		
		IntegranteBuilder integrante = IntegranteBuilder.integrante();
		saveAll();
		
		TrocaPremioBuilder troca1 = TrocaPremioBuilder.trocaPremio(integrante.get(), premio1.get());
		TrocaPremioBuilder troca2 = TrocaPremioBuilder.trocaPremio(integrante.get(), premio2.get());

		saveAll();
		
		MockHttpServletRequestBuilder get = get("/integrante/{integranteId}", integrante.getId());
        
        MvcResult mvcResult = perform(get, status().isOk());
		
		jsonAssert(mvcResult)
			.assertThat("$", hasSize(2))
			.assertThat("$.[*].id", contains(premio1.getId().intValue(), premio2.getId().intValue()));
    }
	
	@Test
	public void testListByPremio() throws Exception{
		PremioBuilder premio1 = PremioBuilder.premio().nome("teste");		
		IntegranteBuilder integrante = IntegranteBuilder.integrante().nome("teste");
		IntegranteBuilder integrante2 = IntegranteBuilder.integrante().nome("teste2");
		saveAll();
		
		Premio premioTeste = premio1.get();
		Integrante integranteTeste = integrante.get();
		Integrante integranteTeste2 = integrante2.get();

		
		TrocaPremioBuilder troca1 = TrocaPremioBuilder.trocaPremio(integranteTeste, premioTeste);
		TrocaPremioBuilder troca2 = TrocaPremioBuilder.trocaPremio(integranteTeste2, premioTeste);

		saveAll();
		
		MockHttpServletRequestBuilder get = get("/premio/{premioId}", premioTeste.getId());
        
        MvcResult mvcResult = perform(get, status().isOk());
		
		jsonAssert(mvcResult)
		.assertThat("$", hasSize(2))
		.assertThat("$.[*].id", contains(troca1.getId().intValue(), troca2.getId().intValue()));
        }
	
	@Test
	public void testListIntegrantesSemPremios() throws Exception{
		PremioBuilder premio = PremioBuilder.premio().nome("premioTeste");
		
		IntegranteBuilder integrante = IntegranteBuilder.integrante().nome("teste");
		IntegranteBuilder integrante2 = IntegranteBuilder.integrante().nome("teste2");
		saveAll();
		
		Premio premioTeste = premio.get();
		Integrante integranteTeste = integrante.get();
		Integrante integranteTeste2 = integrante2.get();

		
		TrocaPremioBuilder.trocaPremio(integranteTeste, premioTeste);
		TrocaPremioBuilder.trocaPremio(integranteTeste, premioTeste);
		saveAll();
				
		MockHttpServletRequestBuilder get = get("/trocaSemPremio");
        
        MvcResult mvcResult = perform(get, status().isOk());
		
		jsonAssert(mvcResult)
		.assertThat("$", hasSize(1));
        }
	
	@Test
	public void testRanking() throws Exception{
		PremioBuilder premio = PremioBuilder.premio().nome("premioTeste");
		PremioBuilder premio2 = PremioBuilder.premio().nome("premioTeste2");
		PremioBuilder premio3 = PremioBuilder.premio().nome("premioTeste3");
		IntegranteBuilder integrante = IntegranteBuilder.integrante().nome("teste");
		IntegranteBuilder integrante2 = IntegranteBuilder.integrante().nome("teste2");
		saveAll();
		
		Premio premioTeste = premio.get();
		Premio premioTeste2 = premio2.get();
		Premio premioTeste3 = premio3.get();

		Integrante integranteTeste = integrante.get();
		Integrante integranteTeste2 = integrante2.get();
		
		TrocaPremioBuilder.trocaPremio(integranteTeste, premioTeste);
		TrocaPremioBuilder.trocaPremio(integranteTeste2, premioTeste2);
		TrocaPremioBuilder.trocaPremio(integranteTeste2, premioTeste2);
		TrocaPremioBuilder.trocaPremio(integranteTeste2, premioTeste2);
		TrocaPremioBuilder.trocaPremio(integranteTeste2, premioTeste3);
		TrocaPremioBuilder.trocaPremio(integranteTeste2, premioTeste3);


		saveAll();
		
		MockHttpServletRequestBuilder mock = get("/rankPremio");
		
		MvcResult mvcResult = perform(mock, status().isOk());
		
		jsonAssert(mvcResult).assertThat("$", hasSize(3))
		.assertThat("$.[*].nome", contains("premioTeste2", "premioTeste3", "premioTeste"));
		
	}
	
	
	@Test
	public void testCreate() throws Exception{
		
		IntegranteBuilder integrante = IntegranteBuilder.integrante();
		PremioBuilder premio = PremioBuilder.premio();
		saveAll();

		
		TrocaPremioForm form = new TrocaPremioForm();
		form.setIntegranteId(integrante.getId());
		form.setPremioId(premio.getId());
		saveAll();
		
		MockHttpServletRequestBuilder mock = post().content(mapper.writeValueAsBytes(form)).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = perform(mock, status().isCreated());
		jsonAssert(mvcResult)
		.assertThat("$.integranteId", equalTo(integrante.getId().intValue()))
		.assertThat("$.premioId", equalTo(premio.getId().intValue()));
			
	}
	
	@Test
	public void testDelete() throws Exception{

		IntegranteBuilder integrante = IntegranteBuilder.integrante();
		PremioBuilder premio = PremioBuilder.premio();
		saveAll();
		
		TrocaPremioBuilder trocaPremio = TrocaPremioBuilder.trocaPremio(integrante.get(), premio.get());
		saveAll();
		
		MockHttpServletRequestBuilder mock = delete("/{id}", trocaPremio.getId());
		MvcResult mvcResult = perform(mock, status().isOk());
		jsonAssert(mvcResult)
		.assertThat("$.id", equalTo(trocaPremio.getId().intValue()));
	}
	
}
