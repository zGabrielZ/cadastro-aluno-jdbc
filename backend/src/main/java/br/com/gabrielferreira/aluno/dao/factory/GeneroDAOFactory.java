package br.com.gabrielferreira.aluno.dao.factory;

import br.com.gabrielferreira.aluno.model.Genero;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneroDAOFactory {

    private GeneroDAOFactory() {}

    public static Genero toFromModel(ResultSet resultSet) throws SQLException {
        return Genero.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
