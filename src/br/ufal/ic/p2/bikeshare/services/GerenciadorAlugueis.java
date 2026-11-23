package br.ufal.ic.p2.bikeshare.services;

import java.time.Duration;
import java.time.LocalDateTime;

import br.ufal.ic.p2.bikeshare.exceptions.AluguelNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.BicicletaNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;
import br.ufal.ic.p2.bikeshare.exceptions.EstacaoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.p2.bikeshare.models.Aluguel;
import br.ufal.ic.p2.bikeshare.models.Bicicleta;
import br.ufal.ic.p2.bikeshare.models.Configuracao;
import br.ufal.ic.p2.bikeshare.models.Estacao;
import br.ufal.ic.p2.bikeshare.models.Usuario;

public class GerenciadorAlugueis {
    private EstadoSistema estado;

    public GerenciadorAlugueis(EstadoSistema estado) {
        this.estado = estado;
    }

    public void atualizarEstado(EstadoSistema novoEstado) {
        this.estado = novoEstado;
    }

    public String retirarBicicleta(String idUsuario, String idEstacao, String idBicicleta, String dataStr) {
        Usuario usuario = buscarUsuario(idUsuario);
        Estacao estacao = buscarEstacao(idEstacao);
        Bicicleta bicicleta = buscarBicicleta(idBicicleta);
        LocalDateTime dataHora = DataUtil.parseDataHora(dataStr);

        if ("inadimplente".equals(usuario.getEstado())) {
            throw new DadoInvalidoException("Usuario inadimplente.");
        }
        if (usuario.getAluguelAtivo() != null) {
            throw new DadoInvalidoException("Usuario ja possui um aluguel em andamento.");
        }
        if (!estacao.getId().equals(bicicleta.getEstacaoId())) {
            throw new DadoInvalidoException("Bicicleta nao esta nesta estacao.");
        }
        if (!"disponivel".equals(bicicleta.getEstado())) {
            throw new DadoInvalidoException("Bicicleta nao esta disponivel.");
        }

        bicicleta.validarRetirada(estado.getConfiguracao());
        usuario.getMetodoPagamento().validarParaRetirada();

        String idAluguel = estado.proximoIdAluguel();
        String inicioFormatado = DataUtil.formatarDataHora(dataHora);
        Aluguel aluguel = new Aluguel(idAluguel, usuario.getId(), bicicleta.getId(), estacao.getId(), inicioFormatado);

        estacao.removerBicicletaId(bicicleta.getId());
        bicicleta.setEstacaoId(null);
        bicicleta.setEstado("emUso");
        usuario.setAluguelAtivo(idAluguel);
        estado.getAlugueis().put(idAluguel, aluguel);

        return idAluguel;
    }

    // Finaliza, cancela ou expira um aluguel em andamento aplicando tarifas, carencia ou penalidade
    public void devolverBicicleta(String idAluguel, String idEstacao, String dataStr) {
        Aluguel aluguel = buscarAluguel(idAluguel);
        Estacao estacaoDestino = buscarEstacao(idEstacao);
        LocalDateTime dataFim = DataUtil.parseDataHora(dataStr);

        if (!"emAndamento".equals(aluguel.getEstado())) {
            throw new DadoInvalidoException("Aluguel nao esta em andamento.");
        }

        LocalDateTime dataInicio = DataUtil.parseDataHora(aluguel.getInicio());
        if (dataFim.isBefore(dataInicio)) {
            throw new DadoInvalidoException("Data de devolucao anterior a data de retirada.");
        }

        if (estacaoDestino.getVagasDisponiveis() <= 0) {
            throw new DadoInvalidoException("Estacao cheia.");
        }

        Usuario usuario = estado.getUsuarios().get(aluguel.getUsuarioId());
        Bicicleta bicicleta = estado.getBicicletas().get(aluguel.getBicicletaId());
        Configuracao config = estado.getConfiguracao();
        long minutos = Duration.between(dataInicio, dataFim).toMinutes();

        String estadoFinal;
        double valorCobrado = 0.0;
        String estadoBicicletaFinal = "disponivel";

        if (minutos > config.getMinutosMaximosAluguel()) {
            estadoFinal = "expirado";
            valorCobrado = 0.0;
            estadoBicicletaFinal = "emManutencao";
            usuario.setEstado("inadimplente");
            usuario.setTotalPendencias(usuario.getTotalPendencias() + config.getValorPenalidadeExpiracao());
        } else if (minutos <= config.getMinutosCarenciaMesmaEstacao()
                && estacaoDestino.getId().equals(aluguel.getEstacaoOrigemId())) {
            estadoFinal = "cancelado";
            valorCobrado = 0.0;
        } else {
            estadoFinal = "finalizado";
            double valorBase = usuario.getPlano().calcularValorBase(minutos, config);
            double adicionalBike = minutos * bicicleta.calcularAdicionalMinuto(config);
            valorCobrado = valorBase + adicionalBike;
            usuario.cobrarValor(valorCobrado);
        }

        aluguel.setEstacaoDestinoId(estacaoDestino.getId());
        aluguel.setFim(DataUtil.formatarDataHora(dataFim));
        aluguel.setEstado(estadoFinal);
        aluguel.setValorCobrado(valorCobrado);

        bicicleta.setEstado(estadoBicicletaFinal);
        bicicleta.setEstacaoId(estacaoDestino.getId());
        estacaoDestino.adicionarBicicletaId(bicicleta.getId());
        usuario.setAluguelAtivo(null);
    }

    public String getAtributoAluguel(String idAluguel, String atributo) {
        Aluguel aluguel = buscarAluguel(idAluguel);
        return aluguel.getAtributo(atributo);
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

    private Estacao buscarEstacao(String idEstacao) {
        if (ehVazio(idEstacao)) {
            throw new DadoInvalidoException("Identificacao da estacao nao pode ser nula.");
        }
        Estacao estacao = estado.getEstacoes().get(idEstacao);
        if (estacao == null) {
            throw new EstacaoNaoExisteException();
        }
        return estacao;
    }

    private Bicicleta buscarBicicleta(String idBicicleta) {
        if (ehVazio(idBicicleta)) {
            throw new DadoInvalidoException("Identificacao da bicicleta nao pode ser nula.");
        }
        Bicicleta bicicleta = estado.getBicicletas().get(idBicicleta);
        if (bicicleta == null) {
            throw new BicicletaNaoExisteException();
        }
        return bicicleta;
    }

    private Aluguel buscarAluguel(String idAluguel) {
        if (ehVazio(idAluguel)) {
            throw new DadoInvalidoException("Identificacao do aluguel nao pode ser nula.");
        }
        Aluguel aluguel = estado.getAlugueis().get(idAluguel);
        if (aluguel == null) {
            throw new AluguelNaoExisteException();
        }
        return aluguel;
    }

    private boolean ehVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}