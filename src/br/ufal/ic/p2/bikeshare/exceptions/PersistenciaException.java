package br.ufal.ic.p2.bikeshare.exceptions;

public class PersistenciaException extends BikeshareException {
    private static final long serialVersionUID = 1L;

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}