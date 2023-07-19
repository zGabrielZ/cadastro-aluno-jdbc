package br.com.gabrielferreira.utils;

public class MascaraUtils {

    private MascaraUtils(){}

    public static String limparMascaraCpf(String cpf){
        cpf = cpf.replace(".","");
        cpf = cpf.replace("-","");
        return cpf;
    }
}
