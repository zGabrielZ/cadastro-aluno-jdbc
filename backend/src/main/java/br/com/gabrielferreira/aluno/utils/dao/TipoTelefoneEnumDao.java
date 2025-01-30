package br.com.gabrielferreira.aluno.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoTelefoneEnumDao {
    FIND_BY_CODIGO_SQL("SELECT T.ID AS ID, T.DESCRICAO AS DESCRICAO, T.CODIGO AS CODIGO FROM TIPO_TELEFONE T WHERE T.CODIGO = ?"),
    FIND_BY_ID_SQL("SELECT T.ID AS ID, T.DESCRICAO AS DESCRICAO, T.CODIGO AS CODIGO FROM TIPO_TELEFONE T WHERE T.ID = ?");

    private final String sql;

}
