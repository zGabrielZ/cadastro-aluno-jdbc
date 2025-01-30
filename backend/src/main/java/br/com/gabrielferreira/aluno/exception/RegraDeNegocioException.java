package br.com.gabrielferreira.aluno.exception;

import java.io.Serial;

public class RegraDeNegocioException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 89885484378280620L;

    public RegraDeNegocioException(String msg){
        super(msg);
    }
}
