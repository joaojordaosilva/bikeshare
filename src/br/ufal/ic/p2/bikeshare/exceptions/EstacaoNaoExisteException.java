package br.ufal.ic.p2.bikeshare.exceptions;

public class EstacaoNaoExisteException extends BikeshareException {
    private static final long serialVersionUID = 1L;

    public EstacaoNaoExisteException() {
        super("Estacao nao existe.");
    }
}