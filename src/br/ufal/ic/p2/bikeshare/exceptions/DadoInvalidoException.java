package br.ufal.ic.p2.bikeshare.exceptions;

public class DadoInvalidoException extends BikeshareException {
    private static final long serialVersionUID = 1L;

    public DadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}