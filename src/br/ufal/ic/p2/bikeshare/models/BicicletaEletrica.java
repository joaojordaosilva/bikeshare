package br.ufal.ic.p2.bikeshare.models;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public class BicicletaEletrica extends Bicicleta {
    private static final long serialVersionUID = 1L;

    private int nivelBateria;

    public BicicletaEletrica() {
    }

    public BicicletaEletrica(String id, String estacaoId, int nivelBateria) {
        super(id, estacaoId);
        this.nivelBateria = nivelBateria;
    }

    @Override
    public String getTipo() {
        return "eletrica";
    }

    @Override
    public boolean isDisponivelParaAluguel(Configuracao config) {
        return super.isDisponivelParaAluguel(config) && this.nivelBateria >= config.getBateriaMinimaEletrica();
    }

    // Impede a retirada caso a bateria esteja abaixo do limite minimo configurado
    @Override
    public void validarRetirada(Configuracao config) {
        if (this.nivelBateria < config.getBateriaMinimaEletrica()) {
            throw new DadoInvalidoException("Nivel de bateria insuficiente.");
        }
    }

    @Override
    public String getAtributo(String atributo) {
        if ("nivelBateria".equals(atributo)) {
            return String.valueOf(this.nivelBateria);
        }
        return super.getAtributo(atributo);
    }

    public int getNivelBateria() {
        return nivelBateria;
    }

    public void setNivelBateria(int nivelBateria) {
        this.nivelBateria = nivelBateria;
    }
    
    @Override
    public double calcularAdicionalMinuto(Configuracao config) {
        return config.getAdicionalMinutoEletrica();
    }
}