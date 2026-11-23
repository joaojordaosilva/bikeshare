package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Estacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String endereco;
    private int capacidade;
    private List<String> bicicletasIds;

    public Estacao() {
        this.bicicletasIds = new ArrayList<>();
    }

    public Estacao(String id, String nome, String endereco, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.capacidade = capacidade;
        this.bicicletasIds = new ArrayList<>();
    }

    public int getVagasDisponiveis() {
        return this.capacidade - this.bicicletasIds.size();
    }

    public int getBicicletasDisponiveis(Map<String, Bicicleta> todasBicicletas, Configuracao config) {
        int disponiveis = 0;
        for (String bikeId : this.bicicletasIds) {
            Bicicleta bike = todasBicicletas.get(bikeId);
            if (bike != null && bike.isDisponivelParaAluguel(config)) {
                disponiveis++;
            }
        }
        return disponiveis;
    }

    public void adicionarBicicletaId(String bicicletaId) {
        this.bicicletasIds.add(bicicletaId);
    }

    public void removerBicicletaId(String bicicletaId) {
        this.bicicletasIds.remove(bicicletaId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<String> getBicicletasIds() {
        return bicicletasIds;
    }

    public void setBicicletasIds(List<String> bicicletasIds) {
        this.bicicletasIds = bicicletasIds;
    }
}