package br.ufal.ic.p2.bikeshare.models;

import java.io.Serializable;
import java.util.Locale;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private Plano plano;
    private MetodoPagamento metodoPagamento;
    private String aluguelAtivo;
    private double totalPendencias;
    private double saldoGuardado;

    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String senha, Plano plano) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = "ativo";
        this.plano = plano;
        this.metodoPagamento = new MetodoPagamento();
        this.aluguelAtivo = null;
        this.totalPendencias = 0.0;
        this.saldoGuardado = 0.0;
    }

    public abstract String getTipo();

    public String getAtributo(String atributo) {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new DadoInvalidoException("Atributo nao existe.");
        }
        switch (atributo) {
            case "nome":
                return this.nome;
            case "email":
                return this.email;
            case "senha":
                return this.senha;
            case "tipo":
                return getTipo();
            case "estado":
                return this.estado;
            case "plano":
                return this.plano.getNome();
            case "mensalidade":
                return this.plano.getMensalidadeFormatada();
            case "aluguelAtivo":
                return this.aluguelAtivo == null ? "nenhum" : this.aluguelAtivo;
            case "totalPendencias":
                return String.format(Locale.forLanguageTag("pt-BR"), "%.2f", this.totalPendencias);
            case "metodoPagamento":
                return this.metodoPagamento.getTipo();
            case "numeroCartao":
                return this.metodoPagamento.getNumeroCartao();
            case "validadeCartao":
                return this.metodoPagamento.getValidadeCartao();
            case "chavePix":
                return this.metodoPagamento.getChavePix();
            case "saldo":
                return this.metodoPagamento.getSaldoFormatado();
            case "cpf":
                throw new DadoInvalidoException("Usuario nao eh pessoa fisica.");
            case "cnpj":
            case "razaoSocial":
                throw new DadoInvalidoException("Usuario nao eh pessoa juridica.");
            default:
                throw new DadoInvalidoException("Atributo nao existe.");
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getAluguelAtivo() {
        return aluguelAtivo;
    }

    public void setAluguelAtivo(String aluguelAtivo) {
        this.aluguelAtivo = aluguelAtivo;
    }

    public double getTotalPendencias() {
        return totalPendencias;
    }

    public void setTotalPendencias(double totalPendencias) {
        this.totalPendencias = totalPendencias;
    }
    
    public void ativarSaldoPreCarregado() {
        this.metodoPagamento = new SaldoPreCarregado(this.saldoGuardado);
    }

    public void recarregarSaldo(String valorStr) {
        this.saldoGuardado = this.metodoPagamento.adicionarSaldo(valorStr);
    }

    public double getSaldoGuardado() {
        return saldoGuardado;
    }

    public void setSaldoGuardado(double saldoGuardado) {
        this.saldoGuardado = saldoGuardado;
    }
    
    public void cobrarValor(double valor) {
        this.metodoPagamento.cobrar(valor);
        if ("saldoPreCarregado".equals(this.metodoPagamento.getTipo())) {
            this.saldoGuardado = Double.parseDouble(
                    this.metodoPagamento.getSaldoFormatado().replace(",", "."));
        }
    }
}