package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public class MetodoPagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    public MetodoPagamento() {
    }

    public String getTipo() {
        return "nenhum";
    }

    // Bloqueia a retirada quando nao ha metodo de pagamento cadastrado
    public void validarParaRetirada() {
        throw new DadoInvalidoException("Metodo de pagamento nao cadastrado.");
    }

    public String getNumeroCartao() {
        throw new DadoInvalidoException("Usuario nao usa cartao.");
    }

    public String getValidadeCartao() {
        throw new DadoInvalidoException("Usuario nao usa cartao.");
    }

    public String getChavePix() {
        throw new DadoInvalidoException("Usuario nao usa pix.");
    }

    public String getSaldoFormatado() {
        throw new DadoInvalidoException("Usuario nao usa saldo pre-carregado.");
    }

    public double adicionarSaldo(String valorStr) {
        throw new DadoInvalidoException("Usuario nao usa saldo pre-carregado.");
    }
    
    public void cobrar(double valor) {
    }
}