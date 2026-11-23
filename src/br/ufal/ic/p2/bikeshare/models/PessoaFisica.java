package br.ufal.ic.p2.bikeshare.models;

public class PessoaFisica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(String id, String nome, String email, String senha, Plano plano, String cpf) {
        super(id, nome, email, senha, plano);
        this.cpf = cpf;
    }

    @Override
    public String getTipo() {
        return "pessoaFisica";
    }

    @Override
    public String getAtributo(String atributo) {
        if ("cpf".equals(atributo)) {
            return this.cpf;
        }
        return super.getAtributo(atributo);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}