package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;
import java.util.Locale;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public class Plano implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private Double mensalidade; // null para plano avulso

    public Plano() {
    }

    public Plano(String nome, Double mensalidade) {
        this.nome = nome;
        this.mensalidade = mensalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Double mensalidade) {
        this.mensalidade = mensalidade;
    }

    public String getMensalidadeFormatada() {
        if (mensalidade == null) {
            throw new DadoInvalidoException("Plano nao possui mensalidade.");
        }
        return String.format(Locale.forLanguageTag("pt-BR"), "%.2f", mensalidade);
    }
    
    public double calcularValorBase(long minutos, Configuracao config) {
        if ("assinante".equals(this.nome)) {
            long excedente = Math.max(0L, minutos - config.getFranquiaMinutosAssinante());
            return excedente * config.getValorMinutoExcedenteAssinante();
        }
        return minutos * config.getValorMinutoAvulso();
    }
}