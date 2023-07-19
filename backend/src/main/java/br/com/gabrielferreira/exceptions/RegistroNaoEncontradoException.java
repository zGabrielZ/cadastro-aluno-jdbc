package br.com.gabrielferreira.exceptions;

import java.io.Serial;

public class RegistroNaoEncontradoException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 89885484378280620L;

    public RegistroNaoEncontradoException(String msg){
        super(msg);
    }
}
