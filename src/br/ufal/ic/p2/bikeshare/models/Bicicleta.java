package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public abstract class Bicicleta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String estacaoId;
    private String estado;

    public Bicicleta() {
    }

    public Bicicleta(String id, String estacaoId) {
        this.id = id;
        this.estacaoId = estacaoId;
        this.estado = "disponivel";
    }

    public abstract String getTipo();

    public boolean isDisponivelParaAluguel(Configuracao config) {
        return "disponivel".equals(this.estado);
    }

    public String getAtributo(String atributo) {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new DadoInvalidoException("Atributo nao existe.");
        }
        switch (atributo) {
            case "tipo":
                return getTipo();
            case "estado":
                return this.estado;
            case "estacao":
                return this.estacaoId == null ? "nenhuma" : this.estacaoId;
            case "nivelBateria":
                throw new DadoInvalidoException("Bicicleta nao possui bateria.");
            default:
                throw new DadoInvalidoException("Atributo nao existe.");
        }
    }

    public void validarRetirada(Configuracao config) {
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstacaoId() {
        return estacaoId;
    }

    public void setEstacaoId(String estacaoId) {
        this.estacaoId = estacaoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public double calcularAdicionalMinuto(Configuracao config) {
        return 0.0;
    }
}