package io.redspark.email.overview.test.controller;
import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.enums.StatusFase;
import io.redspark.email.overview.test.builders.EntregaSprintBuilder;
import io.redspark.email.overview.test.builders.FaseProjetoBuilder;
import io.redspark.email.overview.test.builders.IntegranteBuilder;
import io.redspark.email.overview.test.builders.PremioBuilder;
import io.redspark.email.overview.test.builders.RepositorioBuilder;
import io.redspark.email.overview.test.builders.TrocaPremioBuilder;
import io.redspark.email.overview.test.init.AbstractControllerTest;
import io.redspark.email.overview.test.init.ControllerBase;

@ControllerBase("/integrante")
public class IntegranteControllerTest extends AbstractControllerTest {
	
	@Before
	public void before() throws Exception {
		user();
		saveAll();
		signIn();
	}
	
	@Test
    public void testTokensFuncionarios() throws Exception{

		 	IntegranteBuilder pedro = IntegranteBuilder.integrante().nome("Pedro");
	        IntegranteBuilder jose = IntegranteBuilder.integrante().nome("Jose");
	        IntegranteBuilder maria = IntegranteBuilder.integrante().nome("Maria");
	        IntegranteBuilder ana = IntegranteBuilder.integrante().nome("Ana");
	        IntegranteBuilder fred = IntegranteBuilder.integrante().nome("Fred");
	        saveAll();
	        
	        RepositorioBuilder repositorio1 = RepositorioBuilder.repositorio().nome("repositorio1").time(pedro.get(), jose.get());
	        RepositorioBuilder repositorio2 = RepositorioBuilder.repositorio().nome("repositorio2").time(jose.get(), maria.get(), ana.get(), fred.get());
	        saveAll();
	        
	        Repositorio repositorioTeste = repositorio1.get();
	        Repositorio repositorioTeste2 = repositorio2.get();
	        
	        FaseProjetoBuilder fase1 = FaseProjetoBuilder.faseProjeto(repositorioTeste).status(StatusFase.EM_ANDAMENTO);
	        FaseProjetoBuilder fase2 = FaseProjetoBuilder.faseProjeto(repositorioTeste2).status(StatusFase.EM_ANDAMENTO);
	        FaseProjetoBuilder fase3 = FaseProjetoBuilder.faseProjeto(repositorioTeste2).status(StatusFase.ENTREGUE);

	        saveAll();
	        
	        EntregaSprintBuilder.entregaSprint(fase1.get()).entregue(false).integrantes(pedro.get(), jose.get());
	        EntregaSprintBuilder.entregaSprint(fase2.get()).entregue(true).integrantes(jose.get(), maria.get(), ana.get(), fred.get());
	        EntregaSprintBuilder.entregaSprint(fase3.get()).entregue(true).integrantes(ana.get(), fred.get());
	        saveAll();
        
        MockHttpServletRequestBuilder mock = get("/tokens");
        
        MvcResult mvcResult = perform(mock, status().isOk());
        
        jsonAssert(mvcResult).assertThat("$", hasSize(5))
        .assertThat("$.[0].nome", equalTo("Ana"))
        .assertThat("$.[0].sparks", equalTo(3))
        .assertThat("$.[1].nome", equalTo("Fred"))
        .assertThat("$.[1].sparks", equalTo(3))
        .assertThat("$.[2].nome", equalTo("Jose"))
        .assertThat("$.[2].sparks", equalTo(1))
        .assertThat("$.[3].nome", equalTo("Maria"))
        .assertThat("$.[3].sparks", equalTo(1))
        .assertThat("$.[4].nome", equalTo("Pedro"))
        .assertThat("$.[4].sparks", equalTo(0));
                
    }
	
	@Test
	public void testBuscaTrocaPorId() throws Exception{
		IntegranteBuilder goku = IntegranteBuilder.integrante().nome("Goku");
		saveAll();
		
		RepositorioBuilder repositorio1 = RepositorioBuilder.repositorio().nome("repositorio1").time(goku.get());
		
		Repositorio repositorioTeste = repositorio1.get();
	    
	        
	    FaseProjetoBuilder fase1 = FaseProjetoBuilder.faseProjeto(repositorioTeste).status(StatusFase.ENTREGUE);
	      
	    saveAll();
	        
	    EntregaSprintBuilder.entregaSprint(fase1.get()).entregue(true).integrantes(goku.get());
	    saveAll();
       
		MockHttpServletRequestBuilder mock = get("/tokens/{id}", goku.getId());
        
        MvcResult mvcResult = perform(mock, status().isOk());
        
        jsonAssert(mvcResult).assertThat("$.id", equalTo(goku.getId().intValue()));
        jsonAssert(mvcResult).assertThat("$.nome", equalTo("Goku"));
        jsonAssert(mvcResult).assertThat("$.sparks", equalTo(2));
	}
	

	@Test
	public void deveriaListarTotalToken() throws Exception{
		IntegranteBuilder goku = IntegranteBuilder.integrante().nome("Goku");
		PremioBuilder ps4 = PremioBuilder.premio().nome("PS4").valor(1);
		saveAll();
		
		RepositorioBuilder repositorio1 = RepositorioBuilder.repositorio().nome("repositorio1").time(goku.get());
		saveAll();
		Repositorio repositorioTeste = repositorio1.get();
	   
	    FaseProjetoBuilder fase1 = FaseProjetoBuilder.faseProjeto(repositorioTeste).status(StatusFase.ENTREGUE);
	       
	    saveAll();
	        
	    EntregaSprintBuilder.entregaSprint(fase1.get()).entregue(true).integrantes(goku.get());
	    saveAll();
	    
	    TrocaPremioBuilder.trocaPremio(goku.get(), ps4.get());
	    saveAll();
       
		MockHttpServletRequestBuilder mock = get("/gastos/{id}", goku.getId());
        
        MvcResult mvcResult = perform(mock, status().isOk());
        
        jsonAssert(mvcResult).assertThat("$.id", equalTo(goku.getId().intValue()));
        jsonAssert(mvcResult).assertThat("$.nome", equalTo("Goku"));
        jsonAssert(mvcResult).assertThat("$.sparks", equalTo(1));
	}

}
