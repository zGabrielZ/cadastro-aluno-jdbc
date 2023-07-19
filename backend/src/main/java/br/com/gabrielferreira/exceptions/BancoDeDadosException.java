package br.com.gabrielferreira.exceptions;

import java.io.Serial;

public class BancoDeDadosException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 89885484378280620L;

    public BancoDeDadosException(String msg){
        super(msg);
    }
}
