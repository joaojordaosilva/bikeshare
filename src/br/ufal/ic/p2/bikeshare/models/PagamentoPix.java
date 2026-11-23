package br.ufal.ic.p2.bikeshare.models;

public class PagamentoPix extends MetodoPagamento {
    private static final long serialVersionUID = 1L;

    private String chave;

    public PagamentoPix() {
    }

    public PagamentoPix(String chave) {
        this.chave = chave;
    }

    @Override
    public String getTipo() {
        return "pix";
    }
    
    @Override
    public void validarParaRetirada() {
    }


    @Override
    public String getChavePix() {
        return this.chave;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}