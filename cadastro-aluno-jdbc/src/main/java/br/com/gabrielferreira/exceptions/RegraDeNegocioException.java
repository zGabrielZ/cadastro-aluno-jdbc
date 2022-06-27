package br.com.gabrielferreira.exceptions;

public class RegraDeNegocioException extends RuntimeException{
    private static final long serialVersionUID = 89885484378280620L;

    public RegraDeNegocioException(String msg){
        super(msg);
    }
}
