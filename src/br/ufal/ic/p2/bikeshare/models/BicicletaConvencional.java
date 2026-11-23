package br.ufal.ic.p2.bikeshare.models;

public class BicicletaConvencional extends Bicicleta {
    private static final long serialVersionUID = 1L;

    public BicicletaConvencional() {
    }

    public BicicletaConvencional(String id, String estacaoId) {
        super(id, estacaoId);
    }

    @Override
    public String getTipo() {
        return "convencional";
    }
}