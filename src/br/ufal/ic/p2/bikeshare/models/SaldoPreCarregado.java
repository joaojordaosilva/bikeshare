package br.ufal.ic.p2.bikeshare.models;

import java.util.Locale;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public class SaldoPreCarregado extends MetodoPagamento {
    private static final long serialVersionUID = 1L;

    private double saldo;

    public SaldoPreCarregado() {
    }

    public SaldoPreCarregado(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    @Override
    public String getTipo() {
        return "saldoPreCarregado";
    }

    // Bloqueia a retirada caso o saldo pre-carregado nao seja positivo
    @Override
    public void validarParaRetirada() {
        if (this.saldo <= 0) {
            throw new DadoInvalidoException("Saldo insuficiente.");
        }
    }

    @Override
    public String getSaldoFormatado() {
        return String.format(Locale.forLanguageTag("pt-BR"), "%.2f", this.saldo);
    }

    @Override
    public double adicionarSaldo(String valorStr) {
        if (valorStr == null || valorStr.trim().isEmpty()) {
            throw new DadoInvalidoException("Valor nao pode ser nulo.");
        }
        double valor;
        try {
            valor = Double.parseDouble(valorStr.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new DadoInvalidoException("Valor deve ser numerico.");
        }
        if (valor <= 0) {
            throw new DadoInvalidoException("Valor deve ser positivo.");
        }
        this.saldo += valor;
        return this.saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    @Override
    public void cobrar(double valor) {
        if (this.saldo < valor) {
            throw new DadoInvalidoException("Saldo insuficiente.");
        }
        this.saldo -= valor;
    }
}