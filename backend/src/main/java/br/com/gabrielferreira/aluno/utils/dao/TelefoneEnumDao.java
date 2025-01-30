package br.com.gabrielferreira.aluno.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TelefoneEnumDao {
    INSERT_SQL("INSERT INTO TELEFONE (DDD, NUMERO, ID_USUARIO, ID_TIPO_TELEFONE) VALUES (?, ?, ?, ?)"),
    FIND_BY_ID_USUARIO_SQL("SELECT T.ID AS ID, T.DDD AS DDD, T.NUMERO AS NUMERO, " +
            "TT.ID AS ID_TIPO_TELEFONE, TT.DESCRICAO AS DESCRICAO_TIPO_TELEFONE, TT.CODIGO AS CODIGO_TIPO_TELEFONE FROM TELEFONE T " +
            "LEFT JOIN TIPO_TELEFONE TT ON TT.ID = T.ID_TIPO_TELEFONE " +
            "WHERE T.ID_USUARIO = ?"),
    DELETE_ALL("DELETE FROM TELEFONE");

    private final String sql;

}
