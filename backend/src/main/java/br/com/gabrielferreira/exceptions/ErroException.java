package br.com.gabrielferreira.exceptions;

public class ErroException extends RuntimeException{
    private static final long serialVersionUID = 89885484378280620L;

    public ErroException(String msg){
        super(msg);
    }
}
