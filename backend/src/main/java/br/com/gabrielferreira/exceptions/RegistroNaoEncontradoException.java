package br.com.gabrielferreira.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 89885484378280620L;

    public RegistroNaoEncontradoException(String msg){
        super(msg);
    }
}
