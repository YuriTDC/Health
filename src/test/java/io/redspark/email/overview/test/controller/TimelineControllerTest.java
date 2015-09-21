package io.redspark.email.overview.test.controller;

import static io.redspark.email.overview.test.builders.UserBuilder.user;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@ControllerBase("/timeline")
public class TimelineControllerTest extends AbstractControllerTest {

    @Before
    public void before() throws Exception {
        user();
        saveAll();
        signIn();

    }

    @Test
    public void listTimeline() throws Exception {

        IntegranteBuilder pedro = IntegranteBuilder.integrante().nome("Pedro");
        IntegranteBuilder jose = IntegranteBuilder.integrante().nome("Jose");
        IntegranteBuilder maria = IntegranteBuilder.integrante().nome("Maria");
        IntegranteBuilder ana = IntegranteBuilder.integrante().nome("Ana");
        IntegranteBuilder fred = IntegranteBuilder.integrante().nome("Fred");

        PremioBuilder xbox = PremioBuilder.premio().nome("XBOX").valor(1);
        PremioBuilder ps4 = PremioBuilder.premio().nome("PS4").valor(1);
        saveAll();

        RepositorioBuilder repositorio1 = RepositorioBuilder.repositorio().nome("repositorio1").time(pedro.get(), jose.get());
        RepositorioBuilder repositorio2 = RepositorioBuilder.repositorio().nome("repositorio2").time(jose.get(), maria.get(), ana.get(), fred.get());
        saveAll();

        Repositorio repositorioTeste = repositorio1.get();
        Repositorio repositorioTeste2 = repositorio2.get();

        FaseProjetoBuilder fase1 = FaseProjetoBuilder.faseProjeto(repositorioTeste).status(StatusFase.NAO_ENTREGUE);
        FaseProjetoBuilder fase2 = FaseProjetoBuilder.faseProjeto(repositorioTeste2).status(StatusFase.ENTREGUE);
        FaseProjetoBuilder fase3 = FaseProjetoBuilder.faseProjeto(repositorioTeste2).status(StatusFase.ENTREGUE);
        saveAll();

        EntregaSprintBuilder.entregaSprint(fase1.get()).entregue(false).integrantes(pedro.get(), jose.get());
        EntregaSprintBuilder.entregaSprint(fase2.get()).entregue(true).integrantes(jose.get(), maria.get(), ana.get(), fred.get());
        EntregaSprintBuilder.entregaSprint(fase3.get()).entregue(true).integrantes(ana.get(), fred.get());
        saveAll();

        TrocaPremioBuilder.trocaPremio(ana.get(), xbox.get()).dataTroca(LocalDateTime.now().plusDays(1));
        TrocaPremioBuilder.trocaPremio(ana.get(), ps4.get());
        saveAll();


        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        MockHttpServletRequestBuilder mock = get("/timeline");
        MvcResult mvcResult = perform(mock, status().isOk());
        jsonAssert(mvcResult)
        .assertThat("$" + LocalDateTime.now().format(format), hasSize(7))
        .assertThat("$" + LocalDateTime.now().plusDays(1).format(format), hasSize(1));

    }
}