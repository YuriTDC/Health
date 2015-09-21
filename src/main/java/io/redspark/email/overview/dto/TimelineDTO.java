package io.redspark.email.overview.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.TrocaPremio;
import io.redspark.email.overview.enums.TipoEvento;

public class TimelineDTO {

    private Long id;

    private List<IntegranteDTO> time = new ArrayList<IntegranteDTO>();

    private LocalDateTime dataCriacao;

    private String tipoEvento;

    public TimelineDTO() {
    }

    public TimelineDTO(EntregaSprint entregaSprint) {
        this.id = entregaSprint.getId();
        this.tipoEvento = TipoEvento.ENTREGA_SPRINT.toString();
       // this.dataCriacao = entregaSprint.getDataEntrega();
        /*for (Integrante i : entregaSprint.getIntegrantes()) {
            this.time.add(new IntegranteDTO(i));
        }*/
    }

    public TimelineDTO(FaseProjeto faseProjeto) {
        this.id = faseProjeto.getId();
       // this.dataCriacao = faseProjeto.getDataCriacao();
        this.tipoEvento = TipoEvento.ENTREGA_FASE.toString();
 /*       for (Integrante i : faseProjeto.getRepositorio().getTime()) {
            this.time.add(new IntegranteDTO(i));
        }*/
    }

    public TimelineDTO(TrocaPremio trocas) {
        this.id = trocas.getId();
        this.dataCriacao = trocas.getDataTroca();
        this.tipoEvento = TipoEvento.ENTREGA_TROCA_PREMIO.toString();
        this.time.add(new IntegranteDTO(trocas.getIntegrante()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<IntegranteDTO> getTime() {
        return time;
    }

    public void setTime(List<IntegranteDTO> time) {
        this.time = time;
    }
}