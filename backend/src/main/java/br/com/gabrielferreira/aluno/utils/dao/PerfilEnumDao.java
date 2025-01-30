package br.com.gabrielferreira.aluno.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerfilEnumDao {
    FIND_BY_ID_SQL("SELECT P.ID AS ID, P.DESCRICAO AS DESCRICAO, P.CODIGO AS CODIGO FROM PERFIL P WHERE P.ID = ?"),
    FIND_BY_CODIGO_SQL("SELECT P.ID AS ID, P.DESCRICAO AS DESCRICAO, P.CODIGO AS CODIGO FROM PERFIL P WHERE P.CODIGO = ?");

    private final String sql;

}
