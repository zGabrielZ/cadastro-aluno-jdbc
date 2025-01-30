package br.com.gabrielferreira.aluno.utils;

import br.com.gabrielferreira.aluno.exception.RegraDeNegocioException;

public class ConstantesUtils {

    private ConstantesUtils(){}

    public static void validarCampoVazio(String campo, String msg){
        if(campo == null || campo.isBlank()){
            throw new RegraDeNegocioException(msg);
        }
    }

    public static void validarTamanho(String campo, int inicio, int fim, String msg){
        if(!(campo.length() >= inicio && campo.length() <= fim)){
            throw new RegraDeNegocioException(msg);
        }
    }
}
