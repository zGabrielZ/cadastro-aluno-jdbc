package br.com.gabrielferreira.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BancoDeDadosAmbienteEnum {

    TESTE("test"),
    DESENVOLVIMENTO("dev");

    private final String descricao;
}
