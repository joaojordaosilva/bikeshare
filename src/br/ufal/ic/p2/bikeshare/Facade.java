package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.services.EstadoSistema;
import br.ufal.ic.p2.bikeshare.services.GerenciadorAlugueis;
import br.ufal.ic.p2.bikeshare.services.GerenciadorEstacoes;
import br.ufal.ic.p2.bikeshare.services.GerenciadorUsuarios;
import br.ufal.ic.p2.bikeshare.services.PersistenciaXML;

public class Facade {

    private static final String AINDA_NAO_IMPLEMENTADO = "Ainda não implementado.";

    private EstadoSistema estado;
    private PersistenciaXML persistencia;
    private GerenciadorUsuarios gerenciadorUsuarios;
    private GerenciadorEstacoes gerenciadorEstacoes;
    private GerenciadorAlugueis gerenciadorAlugueis;

    // Carrega o estado salvo em XML (ou inicia um estado vazio) ao instanciar a Facade
    public Facade() {
        this.persistencia = new PersistenciaXML();
        this.estado = persistencia.carregar();
        this.gerenciadorUsuarios = new GerenciadorUsuarios(this.estado);
        this.gerenciadorEstacoes = new GerenciadorEstacoes(this.estado);
        this.gerenciadorAlugueis = new GerenciadorAlugueis(this.estado);
    }

    // US1 - Cadastro de usuario (avulso por padrao quando o plano nao e informado)
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpfOuCnpj) {
        return gerenciadorUsuarios.criarPessoaFisica(nome, email, senha, tipo, cpfOuCnpj, "avulso", null);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial) {
        return gerenciadorUsuarios.criarPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial, "avulso", null);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf,
                                String plano, String mensalidade) {
        return gerenciadorUsuarios.criarPessoaFisica(nome, email, senha, tipo, cpf, plano, mensalidade);
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj,
                                String razaoSocial, String plano, String mensalidade) {
        return gerenciadorUsuarios.criarPessoaJuridica(nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade);
    }

    // US2 - Consulta de usuario
    public String getAtributoUsuario(String usuario, String atributo) {
        return gerenciadorUsuarios.getAtributoUsuario(usuario, atributo);
    }

    public String getUsuarioPorEmail(String email) {
        return gerenciadorUsuarios.getUsuarioPorEmail(email);
    }

    // US3 - Alteracao de usuario
    public void alterarUsuario(String usuario, String atributo, String valor) {
        gerenciadorUsuarios.alterarUsuario(usuario, atributo, valor);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String chave) {
        gerenciadorUsuarios.alterarUsuario(usuario, atributo, valor1, chave);
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String numero, String validade) {
        gerenciadorUsuarios.alterarUsuario(usuario, atributo, valor1, numero, validade);
    }

    // US4 - Remocao de usuario
    public void removerUsuario(String usuario) {
        gerenciadorUsuarios.removerUsuario(usuario);
    }

    // US5 - Cadastro de estacao
    public String criarEstacao(String nome, String endereco, String capacidade) {
        return gerenciadorEstacoes.criarEstacao(nome, endereco, capacidade);
    }

    // US6 - Gestao de bicicletas na estacao
    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) {
        gerenciadorEstacoes.adicionarBicicleta(estacao, bicicleta, tipo, null);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) {
        gerenciadorEstacoes.adicionarBicicleta(estacao, bicicleta, tipo, nivelBateria);
    }

    public void removerBicicleta(String estacao, String bicicleta) {
        gerenciadorEstacoes.removerBicicleta(estacao, bicicleta);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) {
        return gerenciadorEstacoes.getAtributoBicicleta(bicicleta, atributo);
    }

    // US7 - Consulta de disponibilidade
    public String getBicicletasDisponiveis(String estacao) {
        return gerenciadorEstacoes.getBicicletasDisponiveis(estacao);
    }

    public String getVagasDisponiveis(String estacao) {
        return gerenciadorEstacoes.getVagasDisponiveis(estacao);
    }

    public String getEstadoBicicleta(String bicicleta) {
        return gerenciadorEstacoes.getEstadoBicicleta(bicicleta);
    }

    // US8 - Retirada de bicicleta
    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data) {
        return gerenciadorAlugueis.retirarBicicleta(usuario, estacao, bicicleta, data);
    }

 // US9 - Devolucao de bicicleta
    public void devolverBicicleta(String aluguel, String estacao, String data) {
        gerenciadorAlugueis.devolverBicicleta(aluguel, estacao, data);
    }

    // US10 - Consulta de historico de alugueis
    public String getHistoricoAlugueis(String usuario) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getHistoricoAlugueis(String usuario, String atributo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public String getAtributoAluguel(String aluguel, String atributo) {
        return gerenciadorAlugueis.getAtributoAluguel(aluguel, atributo);
    }

    // US11/US12 - Planos e tarifacao
    public String criarPlano(String nome, String franquiaMinutos, String valorMinutoExcedente, String mensalidade) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // US13 - Regras de bloqueio e penalidade
    public void lancaPenalidade(String usuario, String tipo, String valor, String motivo) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // US14 - Manutencao
    public void lancaDefeito(String bicicleta, String descricao) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void concluiManutencao(String bicicleta) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // US15 - Relatorios
    public String getTotalFaturado(String dataInicial, String dataFinal) {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // US16 - Undo/redo
    public void undo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    public void redo() {
        throw new UnsupportedOperationException(AINDA_NAO_IMPLEMENTADO);
    }

    // Limpa os dados em memoria, atualiza os gerenciadores e remove o arquivo salvo
    public void zerarSistema() {
        this.estado = new EstadoSistema();
        this.gerenciadorUsuarios.atualizarEstado(this.estado);
        this.gerenciadorEstacoes.atualizarEstado(this.estado);
        this.gerenciadorAlugueis.atualizarEstado(this.estado);
        this.persistencia.limparArquivo();
    }

    // Salva o estado atual do sistema em disco
    public void encerrarSistema() {
        this.persistencia.salvar(this.estado);
    }
}