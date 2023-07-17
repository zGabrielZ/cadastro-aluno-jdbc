package br.com.gabrielferreira.exceptions;

public class BancoDeDadosException extends RuntimeException{
    private static final long serialVersionUID = 89885484378280620L;

    public BancoDeDadosException(String msg){
        super(msg);
    }
}
