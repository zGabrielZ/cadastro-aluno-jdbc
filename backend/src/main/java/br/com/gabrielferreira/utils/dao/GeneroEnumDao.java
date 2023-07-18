package br.com.gabrielferreira.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GeneroEnumDao {

    FIND_BY_ID_SQL("SELECT G.ID AS ID, G.DESCRICAO AS DESCRICAO, G.CODIGO AS CODIGO FROM GENERO G WHERE G.ID = ?");

    private final String sql;

}
