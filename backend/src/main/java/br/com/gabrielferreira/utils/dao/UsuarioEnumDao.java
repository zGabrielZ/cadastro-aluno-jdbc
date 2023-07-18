package br.com.gabrielferreira.utils.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UsuarioEnumDao {

    INSERT_SQL("INSERT INTO USUARIO (NOME, EMAIL, SENHA, DATA_NASCIMENTO, CPF, NOME_SOCIAL, ID_GENERO) VALUES (?, ?, ?, ?, ?, ?, ?)"),
    FIND_BY_ID_SQL("SELECT U.ID as ID, U.NOME as NOME, U.EMAIL as EMAIL, U.SENHA AS SENHA, U.DATA_NASCIMENTO as DATA_NASCIMENTO, U.CPF as CPF, " +
                    "U.NOME_SOCIAL as NOME_SOCIAL, G.ID AS ID_GENERO, G.DESCRICAO AS DESCRICAO_GENERO, G.CODIGO AS CODIGO_GENERO " +
                    "from USUARIO U LEFT JOIN GENERO G ON G.ID = U.ID_GENERO " +
                    "WHERE U.ID = ?"),
    DELETE_BY_ID_SQL("DELETE FROM USUARIO WHERE ID = ?"),
    UPDAYE_BY_ID_SQL("UPDATE USUARIO SET NOME = ?, EMAIL = ?, SENHA = ?, DATA_NASCIMENTO = ?, CPF = ?, NOME_SOCIAL = ?, ID_GENERO = ? WHERE ID = ?");

    private final String sql;

}
