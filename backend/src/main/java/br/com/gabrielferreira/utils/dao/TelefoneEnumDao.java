package br.com.gabrielferreira.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TelefoneEnumDao {

    INSERT_SQL("INSERT INTO TELEFONE (DDD, NUMERO, ID_USUARIO, ID_TIPO_TELEFONE) VALUES (?, ?, ?, ?)");

    private final String sql;

}
