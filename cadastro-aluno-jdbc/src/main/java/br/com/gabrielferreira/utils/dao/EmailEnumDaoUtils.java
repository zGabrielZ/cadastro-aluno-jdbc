package br.com.gabrielferreira.utils.dao;

import lombok.Getter;

public enum EmailEnumDaoUtils {

    ID("ID"),
    ENDERECO("ENDERECO");

    @Getter
    private final String valor;

    EmailEnumDaoUtils(String valor){
        this.valor = valor;
    }

}
