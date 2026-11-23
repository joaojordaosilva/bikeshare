package br.ufal.ic.p2.bikeshare.services;

import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.BicicletaConvencional;
import br.ufal.ic.p2.bikeshare.models.BicicletaEletrica;
import br.ufal.ic.p2.bikeshare.models.Estacao;

public class GerenciadorEstacoes {
    private EstadoSistema estado;

    public GerenciadorEstacoes(EstadoSistema estado) {
        this.estado = estado;
    }

    public void atualizarEstado(EstadoSistema novoEstado) {
        this.estado = novoEstado;
    }

    public String criarEstacao(String nome, String endereco, String capacidadeStr) {
        if (ehVazio(nome)) {
            throw new DadoInvalidoException("Nome nao pode ser nulo.");
        }
        if (ehVazio(endereco)) {
            throw new DadoInvalidoException("Endereco nao pode ser nulo.");
        }
        if (ehVazio(capacidadeStr)) {
            throw new DadoInvalidoException("Capacidade nao pode ser nula.");
        }

        int capacidade;
        try {
            capacidade = Integer.parseInt(capacidadeStr.trim());
        } catch (NumberFormatException e) {
            throw new DadoInvalidoException("Capacidade deve ser numerica.");
        }

        if (capacidade <= 0) {
            throw new DadoInvalidoException("Capacidade deve ser positiva.");
        }

        String id = estado.proximoIdEstacao();
        Estacao estacao = new Estacao(id, nome, endereco, capacidade);
        estado.getEstacoes().put(id, estacao);
        return id;
    }

    public void adicionarBicicleta(String idEstacao, String idBicicleta, String tipo, String nivelBateriaStr) {
        Estacao estacao = buscarEstacao(idEstacao);

        if (ehVazio(idBicicleta)) {
            throw new DadoInvalidoException("Identificacao da bicicleta nao pode ser nula.");
        }
        if (estado.getBicicletas().containsKey(idBicicleta)) {
            throw new DadoInvalidoException("Bicicleta ja existe.");
        }

        Bicicleta novaBicicleta;
        if ("convencional".equals(tipo)) {
            if (!ehVazio(nivelBateriaStr)) {
                throw new DadoInvalidoException("Bicicleta nao possui bateria.");
            }
            novaBicicleta = new BicicletaConvencional(idBicicleta, estacao.getId());
        } else if ("eletrica".equals(tipo)) {
            if (ehVazio(nivelBateriaStr)) {
                throw new DadoInvalidoException("Nivel de bateria nao pode ser nulo.");
            }
            int nivelBateria;
            try {
                nivelBateria = Integer.parseInt(nivelBateriaStr.trim());
            } catch (NumberFormatException e) {
                throw new DadoInvalidoException("Nivel de bateria deve ser numerico.");
            }
            if (nivelBateria < 0 || nivelBateria > 100) {
                throw new DadoInvalidoException("Nivel de bateria deve estar entre 0 e 100.");
            }
            novaBicicleta = new BicicletaEletrica(idBicicleta, estacao.getId(), nivelBateria);
        } else {
            throw new DadoInvalidoException("Tipo invalido.");
        }

        if (estacao.getVagasDisponiveis() <= 0) {
            throw new DadoInvalidoException("Estacao sem vagas disponiveis.");
        }

        estado.getBicicletas().put(idBicicleta, novaBicicleta);
        estacao.adicionarBicicletaId(idBicicleta);
    }

    public void removerBicicleta(String idEstacao, String idBicicleta) {
        Estacao estacao = buscarEstacao(idEstacao);
        Bicicleta bicicleta = buscarBicicleta(idBicicleta);

        if (!estacao.getId().equals(bicicleta.getEstacaoId())) {
            throw new DadoInvalidoException("Bicicleta nao esta nesta estacao.");
        }
        if (!"disponivel".equals(bicicleta.getEstado())) {
            throw new DadoInvalidoException("Bicicleta nao esta disponivel.");
        }

        estacao.removerBicicletaId(idBicicleta);
        estado.getBicicletas().remove(idBicicleta);
    }

    public String getAtributoBicicleta(String idBicicleta, String atributo) {
        Bicicleta bicicleta = buscarBicicleta(idBicicleta);
        return bicicleta.getAtributo(atributo);
    }

    public String getEstadoBicicleta(String idBicicleta) {
        Bicicleta bicicleta = buscarBicicleta(idBicicleta);
        return bicicleta.getEstado();
    }

    public String getVagasDisponiveis(String idEstacao) {
        Estacao estacao = buscarEstacao(idEstacao);
        return String.valueOf(estacao.getVagasDisponiveis());
    }

    public String getBicicletasDisponiveis(String idEstacao) {
        Estacao estacao = buscarEstacao(idEstacao);
        int disponiveis = estacao.getBicicletasDisponiveis(estado.getBicicletas(), estado.getConfiguracao());
        return String.valueOf(disponiveis);
    }

    public Estacao buscarEstacao(String idEstacao) {
        if (ehVazio(idEstacao)) {
            throw new DadoInvalidoException("Identificacao da estacao nao pode ser nula.");
        }
        Estacao estacao = estado.getEstacoes().get(idEstacao);
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }

    public Bicicleta buscarBicicleta(String idBicicleta) {
        if (ehVazio(idBicicleta)) {
            throw new DadoInvalidoException("Identificacao da bicicleta nao pode ser nula.");
        }
        Bicicleta bicicleta = estado.getBicicletas().get(idBicicleta);
        if (bicicleta == null) {
            throw new BicicletaNaoExisteException();
        }
        return bicicleta;
    }

    private boolean ehVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}