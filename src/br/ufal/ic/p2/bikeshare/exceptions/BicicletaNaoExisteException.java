package br.ufal.ic.p2.bikeshare.exceptions;

public class BicicletaNaoExisteException extends BikeshareException {
    private static final long serialVersionUID = 1L;

    public BicicletaNaoExisteException() {
        super("Bicicleta nao existe.");
    }
}