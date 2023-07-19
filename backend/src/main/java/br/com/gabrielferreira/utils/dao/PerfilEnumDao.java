package br.com.gabrielferreira.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerfilEnumDao {

    FIND_BY_ID_SQL("SELECT P.ID AS ID, P.DESCRICAO AS DESCRICAO, P.CODIGO AS CODIGO FROM PERFIL P WHERE P.ID = ?");

    private final String sql;

}
