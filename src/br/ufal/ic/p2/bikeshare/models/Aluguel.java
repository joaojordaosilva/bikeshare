package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;
import java.util.Locale;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public class Aluguel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String usuarioId;
    private String bicicletaId;
    private String estacaoOrigemId;
    private String estacaoDestinoId;
    private String inicio;
    private String fim;
    private String estado;
    private Double valorCobrado;

    public Aluguel() {
    }

    public Aluguel(String id, String usuarioId, String bicicletaId, String estacaoOrigemId, String inicio) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.bicicletaId = bicicletaId;
        this.estacaoOrigemId = estacaoOrigemId;
        this.inicio = inicio;
        this.estado = "emAndamento";
    }

    public String getAtributo(String atributo) {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new DadoInvalidoException("Atributo nao existe.");
        }
        switch (atributo) {
            case "estado":
                return this.estado;
            case "usuario":
                return this.usuarioId;
            case "bicicleta":
                return this.bicicletaId;
            case "estacaoOrigem":
                return this.estacaoOrigemId;
            case "inicio":
                return this.inicio;
            case "fim":
                verificarFinalizado();
                return this.fim;
            case "estacaoDestino":
                verificarFinalizado();
                return this.estacaoDestinoId;
            case "valorCobrado":
                verificarFinalizado();
                return String.format(Locale.forLanguageTag("pt-BR"), "%.2f", this.valorCobrado);
            default:
                throw new DadoInvalidoException("Atributo nao existe.");
        }
    }

    private void verificarFinalizado() {
        if ("emAndamento".equals(this.estado)) {
            throw new DadoInvalidoException("Aluguel ainda esta em andamento.");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getBicicletaId() {
        return bicicletaId;
    }

    public void setBicicletaId(String bicicletaId) {
        this.bicicletaId = bicicletaId;
    }

    public String getEstacaoOrigemId() {
        return estacaoOrigemId;
    }

    public void setEstacaoOrigemId(String estacaoOrigemId) {
        this.estacaoOrigemId = estacaoOrigemId;
    }

    public String getEstacaoDestinoId() {
        return estacaoDestinoId;
    }

    public void setEstacaoDestinoId(String estacaoDestinoId) {
        this.estacaoDestinoId = estacaoDestinoId;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }
}