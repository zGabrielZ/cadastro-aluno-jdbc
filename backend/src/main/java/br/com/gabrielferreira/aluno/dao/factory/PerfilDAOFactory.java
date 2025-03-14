package br.com.gabrielferreira.aluno.dao.factory;

import br.com.gabrielferreira.aluno.model.Perfil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerfilDAOFactory {

    private PerfilDAOFactory () {}

    public static Perfil toFromModel(ResultSet resultSet) throws SQLException {
        return Perfil.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
