package br.com.gabrielferreira.aluno.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GeneroEnumDao {
    FIND_BY_ID_SQL("SELECT G.ID AS ID, G.DESCRICAO AS DESCRICAO, G.CODIGO AS CODIGO FROM GENERO G WHERE G.ID = ?"),
    FIND_BY_CODIGO_SQL("SELECT G.ID AS ID, G.DESCRICAO AS DESCRICAO, G.CODIGO AS CODIGO FROM GENERO G WHERE G.CODIGO = ?");

    private final String sql;

}
