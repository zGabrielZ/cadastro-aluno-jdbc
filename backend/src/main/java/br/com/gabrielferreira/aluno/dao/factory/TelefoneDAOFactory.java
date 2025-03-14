package br.com.gabrielferreira.aluno.dao.factory;

import br.com.gabrielferreira.aluno.model.Telefone;
import br.com.gabrielferreira.aluno.model.TipoTelefone;

import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.aluno.dao.factory.TipoTelefoneDAOFactory.toTipoTelefone;

public class TelefoneDAOFactory {

    private TelefoneDAOFactory() {}

    public static Telefone toFromModel(ResultSet resultSet) throws SQLException {
        TipoTelefone tipoTelefone = toTipoTelefone(resultSet);

        return Telefone.builder()
                .id(resultSet.getLong("ID"))
                .ddd(resultSet.getString("DDD"))
                .numero(resultSet.getString("NUMERO"))
                .tipoTelefone(tipoTelefone)
                .build();
    }
}
