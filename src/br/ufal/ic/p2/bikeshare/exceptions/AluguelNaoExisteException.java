package br.ufal.ic.p2.bikeshare.exceptions;

public class AluguelNaoExisteException extends BikeshareException {
    private static final long serialVersionUID = 1L;

    public AluguelNaoExisteException() {
        super("Aluguel nao existe.");
    }
}