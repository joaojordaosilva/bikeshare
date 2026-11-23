package br.ufal.ic.p2.bikeshare.models;

public class PagamentoCartao extends MetodoPagamento {
    private static final long serialVersionUID = 1L;

    private String numero;
    private String validade;

    public PagamentoCartao() {
    }

    public PagamentoCartao(String numero, String validade) {
        this.numero = numero;
        this.validade = validade;
    }

    @Override
    public String getTipo() {
        return "cartao";
    }

    // Cartao valido autoriza a retirada
    @Override
    public void validarParaRetirada() {
    }

    @Override
    public String getNumeroCartao() {
        return this.numero;
    }

    @Override
    public String getValidadeCartao() {
        return this.validade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}