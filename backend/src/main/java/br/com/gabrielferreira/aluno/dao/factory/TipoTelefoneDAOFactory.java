package br.com.gabrielferreira.aluno.dao.factory;

import br.com.gabrielferreira.aluno.model.TipoTelefone;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoTelefoneDAOFactory {

    private TipoTelefoneDAOFactory() {}

    public static TipoTelefone toTipoTelefone(ResultSet resultSet) throws SQLException {
        return TipoTelefone.builder()
                .id(resultSet.getLong("ID_TIPO_TELEFONE"))
                .descricao(resultSet.getString("DESCRICAO_TIPO_TELEFONE"))
                .codigo(resultSet.getString("CODIGO_TIPO_TELEFONE"))
                .build();
    }

    public static TipoTelefone toFromModel(ResultSet resultSet) throws SQLException{
        return TipoTelefone.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
