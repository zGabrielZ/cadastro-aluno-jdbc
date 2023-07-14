package br.com.gabrielferreira.utils.dao;

import lombok.Getter;

public enum TelefoneEnumDaoUtils {

    ID("ID"),
    NUMERO("NUMERO"),
    ALUNO_ID("ID_ALUNO"),
    TIPO_ID("ID_TIPO"),
    DDD("DDD");

    @Getter
    private final String valor;

    TelefoneEnumDaoUtils(String valor){
        this.valor = valor;
    }

}
