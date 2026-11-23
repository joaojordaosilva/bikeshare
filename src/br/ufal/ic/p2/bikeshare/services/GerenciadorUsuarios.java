package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.PessoaFisica;
import br.ufal.ic.p2.bikeshare.models.PessoaJuridica;
import br.ufal.ic.p2.bikeshare.models.Plano;
import br.ufal.ic.p2.bikeshare.models.Usuario;
import br.ufal.ic.p2.bikeshare.models.PagamentoCartao;
import br.ufal.ic.p2.bikeshare.models.PagamentoPix;

public class GerenciadorUsuarios {
    private EstadoSistema estado;
    
    public String getUsuarioPorEmail(String email) {
        if (ehVazio(email)) {
            throw new DadoInvalidoException("Email nao pode ser nulo.");
        }
        for (Usuario u : estado.getUsuarios().values()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u.getId();
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public GerenciadorUsuarios(EstadoSistema estado) {
        this.estado = estado;
    }

    public void atualizarEstado(EstadoSistema novoEstado) {
        this.estado = novoEstado;
    }

    public String criarPessoaFisica(String nome, String email, String senha, String tipo,
                                    String cpf, String nomePlano, String mensalidadeStr) {
        validarCamposComuns(nome, email, senha);
        if (!"pessoaFisica".equals(tipo)) {
            throw new DadoInvalidoException("Tipo invalido.");
        }
        if (ehVazio(cpf)) {
            throw new DadoInvalidoException("CPF nao pode ser nulo.");
        }
        validarEmailDuplicado(email);
        Plano plano = criarPlanoUsuario(nomePlano, mensalidadeStr);

        String id = estado.proximoIdUsuario();
        Usuario usuario = new PessoaFisica(id, nome, email, senha, plano, cpf);
        estado.getUsuarios().put(id, usuario);
        return id;
    }

    public String criarPessoaJuridica(String nome, String email, String senha, String tipo,
                                      String cnpj, String razaoSocial, String nomePlano, String mensalidadeStr) {
        validarCamposComuns(nome, email, senha);
        if (!"pessoaJuridica".equals(tipo)) {
            throw new DadoInvalidoException("Tipo invalido.");
        }
        if (ehVazio(cnpj)) {
            throw new DadoInvalidoException("CNPJ nao pode ser nulo.");
        }
        if (ehVazio(razaoSocial)) {
            throw new DadoInvalidoException("Razao social nao pode ser nula.");
        }
        validarEmailDuplicado(email);
        Plano plano = criarPlanoUsuario(nomePlano, mensalidadeStr);

        String id = estado.proximoIdUsuario();
        Usuario usuario = new PessoaJuridica(id, nome, email, senha, plano, cnpj, razaoSocial);
        estado.getUsuarios().put(id, usuario);
        return id;
    }

    public String getAtributoUsuario(String idUsuario, String atributo) {
        if (ehVazio(idUsuario)) {
            throw new DadoInvalidoException("Identificacao do usuario nao pode ser nula.");
        }
        Usuario usuario = estado.getUsuarios().get(idUsuario);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }
        return usuario.getAtributo(atributo);
    }
    
    
    

    private void validarCamposComuns(String nome, String email, String senha) {
        if (ehVazio(nome)) {
            throw new DadoInvalidoException("Nome nao pode ser nulo.");
        }
        if (ehVazio(email)) {
            throw new DadoInvalidoException("Email nao pode ser nulo.");
        }
        if (!email.contains("@")) {
            throw new DadoInvalidoException("Email invalido.");
        }
        if (ehVazio(senha)) {
            throw new DadoInvalidoException("Senha nao pode ser nula.");
        }
    }

    private void validarEmailDuplicado(String email) {
        for (Usuario u : estado.getUsuarios().values()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new DadoInvalidoException("Email ja cadastrado.");
            }
        }
    }

    private Plano criarPlanoUsuario(String nomePlano, String mensalidadeStr) {
        if (ehVazio(nomePlano)) {
            throw new DadoInvalidoException("Plano invalido.");
        }
        if ("avulso".equals(nomePlano)) {
            if (!ehVazio(mensalidadeStr)) {
                throw new DadoInvalidoException("Plano nao possui mensalidade.");
            }
            return new Plano("avulso", null);
        }
        if ("assinante".equals(nomePlano)) {
            if (ehVazio(mensalidadeStr)) {
                throw new DadoInvalidoException("Mensalidade nao pode ser nula.");
            }
            double valor;
            try {
                valor = Double.parseDouble(mensalidadeStr.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                throw new DadoInvalidoException("Mensalidade deve ser numerica.");
            }
            if (valor < 0) {
                throw new DadoInvalidoException("Mensalidade deve ser nao-negativa.");
            }
            return new Plano("assinante", valor);
        }
        throw new DadoInvalidoException("Plano invalido.");
    }

    private boolean ehVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
    public void alterarUsuario(String idUsuario, String atributo, String valor) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (ehVazio(atributo)) {
            throw new DadoInvalidoException("Atributo nao pode ser alterado.");
        }
        switch (atributo) {
            case "nome":
                if (ehVazio(valor)) {
                    throw new DadoInvalidoException("Nome nao pode ser nulo.");
                }
                usuario.setNome(valor);
                break;
            case "email":
                if (ehVazio(valor)) {
                    throw new DadoInvalidoException("Email nao pode ser nulo.");
                }
                if (!valor.contains("@")) {
                    throw new DadoInvalidoException("Email invalido.");
                }
                validarEmailDuplicadoOutroUsuario(valor, usuario.getId());
                usuario.setEmail(valor);
                break;
            case "senha":
                if (ehVazio(valor)) {
                    throw new DadoInvalidoException("Senha nao pode ser nula.");
                }
                usuario.setSenha(valor);
                break;
            case "plano":
                alterarPlanoUsuario(usuario, valor);
                break;
            case "metodoPagamento":
                if (!"saldoPreCarregado".equals(valor)) {
                    throw new DadoInvalidoException("Valor de metodo de pagamento invalido.");
                }
                usuario.ativarSaldoPreCarregado();
                break;
            case "saldo":
                usuario.recarregarSaldo(valor);
                break;
            default:
                throw new DadoInvalidoException("Atributo nao pode ser alterado.");
        }
    }

    public void alterarUsuario(String idUsuario, String atributo, String valor1, String chave) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (!"metodoPagamento".equals(atributo)) {
            throw new DadoInvalidoException("Atributo nao pode ser alterado.");
        }
        if (!"pix".equals(valor1)) {
            throw new DadoInvalidoException("Valor de metodo de pagamento invalido.");
        }
        if (ehVazio(chave)) {
            throw new DadoInvalidoException("Chave pix nao pode ser nula.");
        }
        usuario.setMetodoPagamento(new PagamentoPix(chave));
    }

    public void alterarUsuario(String idUsuario, String atributo, String valor1, String numero, String validade) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (!"metodoPagamento".equals(atributo)) {
            throw new DadoInvalidoException("Atributo nao pode ser alterado.");
        }
        if (!"cartao".equals(valor1)) {
            throw new DadoInvalidoException("Valor de metodo de pagamento invalido.");
        }
        if (ehVazio(numero)) {
            throw new DadoInvalidoException("Numero do cartao nao pode ser nulo.");
        }
        if (ehVazio(validade)) {
            throw new DadoInvalidoException("Validade do cartao nao pode ser nula.");
        }
        if (!validade.matches("^(0[1-9]|1[0-2])/\\d{4}$")) {
            throw new DadoInvalidoException("Validade do cartao invalida.");
        }
        usuario.setMetodoPagamento(new PagamentoCartao(numero, validade));
    }

    private Usuario buscarUsuario(String idUsuario) {
        if (ehVazio(idUsuario)) {
            throw new DadoInvalidoException("Identificacao do usuario nao pode ser nula.");
        }
        Usuario usuario = estado.getUsuarios().get(idUsuario);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }
        return usuario;
    }

    private void validarEmailDuplicadoOutroUsuario(String email, String idAtual) {
        for (Usuario u : estado.getUsuarios().values()) {
            if (!u.getId().equals(idAtual) && u.getEmail().equalsIgnoreCase(email)) {
                throw new DadoInvalidoException("Email ja cadastrado.");
            }
        }
    }

    private void alterarPlanoUsuario(Usuario usuario, String nomePlano) {
        if (ehVazio(nomePlano)) {
            throw new DadoInvalidoException("Plano invalido.");
        }
        if (nomePlano.equals(usuario.getPlano().getNome())) {
            return;
        }
        if ("avulso".equals(nomePlano)) {
            usuario.setPlano(new Plano("avulso", null));
        } else if ("assinante".equals(nomePlano)) {
            usuario.setPlano(new Plano("assinante", 30.0));
        } else {
            throw new DadoInvalidoException("Plano invalido.");
        }
    }
    
    public void removerUsuario(String idUsuario) {
        Usuario usuario = buscarUsuario(idUsuario);
        if (usuario.getAluguelAtivo() != null) {
            throw new DadoInvalidoException("Nao e possivel remover usuario com aluguel em andamento.");
        }
        estado.getUsuarios().remove(usuario.getId());
    }
}