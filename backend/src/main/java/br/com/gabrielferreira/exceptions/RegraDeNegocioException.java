package br.com.gabrielferreira.exceptions;

import java.io.Serial;

public class RegraDeNegocioException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 89885484378280620L;

    public RegraDeNegocioException(String msg){
        super(msg);
    }
}
