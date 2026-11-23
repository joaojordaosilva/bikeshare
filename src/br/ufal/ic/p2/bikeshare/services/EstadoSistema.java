package br.ufal.ic.p2.bikeshare.services;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.Configuracao;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class EstadoSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    private Configuracao configuracao;
    private Map<String, Usuario> usuarios;
    private int contadorUsuarios;
    private Map<String, Estacao> estacoes;
    private int contadorEstacoes;
    private Map<String, Bicicleta> bicicletas;
    private Map<String, Aluguel> alugueis;
    private int contadorAlugueis;

    public EstadoSistema() {
        this.configuracao = new Configuracao();
        this.usuarios = new LinkedHashMap<>();
        this.contadorUsuarios = 0;
        this.estacoes = new LinkedHashMap<>();
        this.contadorEstacoes = 0;
        this.bicicletas = new LinkedHashMap<>();
        this.alugueis = new LinkedHashMap<>();
        this.contadorAlugueis = 0;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getContadorUsuarios() {
        return contadorUsuarios;
    }

    public void setContadorUsuarios(int contadorUsuarios) {
        this.contadorUsuarios = contadorUsuarios;
    }

    public String proximoIdUsuario() {
        this.contadorUsuarios++;
        return "U" + this.contadorUsuarios;
    }

    public Map<String, Estacao> getEstacoes() {
        return estacoes;
    }

    public void setEstacoes(Map<String, Estacao> estacoes) {
        this.estacoes = estacoes;
    }

    public int getContadorEstacoes() {
        return contadorEstacoes;
    }

    public void setContadorEstacoes(int contadorEstacoes) {
        this.contadorEstacoes = contadorEstacoes;
    }

    public String proximoIdEstacao() {
        this.contadorEstacoes++;
        return "E" + this.contadorEstacoes;
    }

    public Map<String, Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(Map<String, Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }

    // Armazena os alugueis criados para consulta e persistencia
    public Map<String, Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(Map<String, Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    public int getContadorAlugueis() {
        return contadorAlugueis;
    }

    public void setContadorAlugueis(int contadorAlugueis) {
        this.contadorAlugueis = contadorAlugueis;
    }

    // Gera identificadores sequenciais no padrao A1, A2, ...
    public String proximoIdAluguel() {
        this.contadorAlugueis++;
        return "A" + this.contadorAlugueis;
    }
}