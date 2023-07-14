package br.com.gabrielferreira.utils.dao;

import lombok.Getter;

public enum AlunoEnumDaoUtils {

    ID("ID"),
    NOME("NOME"),
    CPF("CPF"),
    EMAIL_ID("EMAIL_ID");

    @Getter
    private final String valor;

    AlunoEnumDaoUtils(String valor){
        this.valor = valor;
    }

}
