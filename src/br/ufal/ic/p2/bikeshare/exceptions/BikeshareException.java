package br.ufal.ic.p2.bikeshare.exceptions;

public class BikeshareException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BikeshareException(String mensagem) {
        super(mensagem);
    }

    public BikeshareException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}