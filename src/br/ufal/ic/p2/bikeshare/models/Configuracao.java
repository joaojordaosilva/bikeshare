package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;

public class Configuracao implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bateriaMinimaEletrica;
    private double valorMinutoAvulso;
    private int franquiaMinutosAssinante;
    private double valorMinutoExcedenteAssinante;
    private double adicionalMinutoEletrica;
    private int minutosCarenciaMesmaEstacao;
    private int minutosMaximosAluguel;
    private double valorPenalidadeExpiracao;

    public Configuracao() {
        this.bateriaMinimaEletrica = 15;
        this.valorMinutoAvulso = 0.50;
        this.franquiaMinutosAssinante = 60;
        this.valorMinutoExcedenteAssinante = 0.30;
        this.adicionalMinutoEletrica = 0.20;
        this.minutosCarenciaMesmaEstacao = 2;
        this.minutosMaximosAluguel = 1440;
        this.valorPenalidadeExpiracao = 10.00;
    }

    public int getBateriaMinimaEletrica() {
        return bateriaMinimaEletrica;
    }

    public void setBateriaMinimaEletrica(int bateriaMinimaEletrica) {
        this.bateriaMinimaEletrica = bateriaMinimaEletrica;
    }

    public double getValorMinutoAvulso() {
        return valorMinutoAvulso;
    }

    public void setValorMinutoAvulso(double valorMinutoAvulso) {
        this.valorMinutoAvulso = valorMinutoAvulso;
    }

    public int getFranquiaMinutosAssinante() {
        return franquiaMinutosAssinante;
    }

    public void setFranquiaMinutosAssinante(int franquiaMinutosAssinante) {
        this.franquiaMinutosAssinante = franquiaMinutosAssinante;
    }

    public double getValorMinutoExcedenteAssinante() {
        return valorMinutoExcedenteAssinante;
    }

    public void setValorMinutoExcedenteAssinante(double valorMinutoExcedenteAssinante) {
        this.valorMinutoExcedenteAssinante = valorMinutoExcedenteAssinante;
    }

    public double getAdicionalMinutoEletrica() {
        return adicionalMinutoEletrica;
    }

    public void setAdicionalMinutoEletrica(double adicionalMinutoEletrica) {
        this.adicionalMinutoEletrica = adicionalMinutoEletrica;
    }

    public int getMinutosCarenciaMesmaEstacao() {
        return minutosCarenciaMesmaEstacao;
    }

    public void setMinutosCarenciaMesmaEstacao(int minutosCarenciaMesmaEstacao) {
        this.minutosCarenciaMesmaEstacao = minutosCarenciaMesmaEstacao;
    }

    public int getMinutosMaximosAluguel() {
        return minutosMaximosAluguel;
    }

    public void setMinutosMaximosAluguel(int minutosMaximosAluguel) {
        this.minutosMaximosAluguel = minutosMaximosAluguel;
    }

    public double getValorPenalidadeExpiracao() {
        return valorPenalidadeExpiracao;
    }

    public void setValorPenalidadeExpiracao(double valorPenalidadeExpiracao) {
        this.valorPenalidadeExpiracao = valorPenalidadeExpiracao;
    }
}