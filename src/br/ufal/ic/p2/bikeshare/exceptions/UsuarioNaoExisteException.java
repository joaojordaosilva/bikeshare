package br.ufal.ic.p2.bikeshare.exceptions;

public class UsuarioNaoExisteException extends BikeshareException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoExisteException() {
        super("Usuario nao existe.");
    }
}