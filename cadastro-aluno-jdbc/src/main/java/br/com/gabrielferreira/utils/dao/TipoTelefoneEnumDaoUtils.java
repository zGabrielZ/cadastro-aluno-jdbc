package br.com.gabrielferreira.utils.dao;

import lombok.Getter;

public enum TipoTelefoneEnumDaoUtils {

    ID("ID"),
    DESCRICAO("DESCRICAO");

    @Getter
    private String valor;

    TipoTelefoneEnumDaoUtils(String valor){
        this.valor = valor;
    }

}
