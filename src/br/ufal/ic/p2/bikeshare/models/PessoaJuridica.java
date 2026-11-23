package br.ufal.ic.p2.bikeshare.models;

public class PessoaJuridica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cnpj;
    private String razaoSocial;

    public PessoaJuridica() {
    }

    public PessoaJuridica(String id, String nome, String email, String senha, Plano plano,
                          String cnpj, String razaoSocial) {
        super(id, nome, email, senha, plano);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getTipo() {
        return "pessoaJuridica";
    }

    @Override
    public String getAtributo(String atributo) {
        if ("cnpj".equals(atributo)) {
            return this.cnpj;
        }
        if ("razaoSocial".equals(atributo)) {
            return this.razaoSocial;
        }
        return super.getAtributo(atributo);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}