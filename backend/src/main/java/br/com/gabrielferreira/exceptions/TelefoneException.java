package br.com.gabrielferreira.exceptions;

import java.io.Serial;

public class TelefoneException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 89885484378280620L;

    public TelefoneException(String msg){
        super(msg);
    }
}
