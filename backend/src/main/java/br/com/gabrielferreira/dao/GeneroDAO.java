package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Genero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.GeneroEnumDao.*;


public class GeneroDAO extends GenericoDAO<Genero>{

    protected GeneroDAO(Connection connection) {
        super(connection);
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
    }

    @Override
    protected void toInsertOrUpdate(Genero entidade, Long id, PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException("No futuro vai ser implementado o método");
    }

    @Override
    protected void toIdEntityInsert(Genero entidade, ResultSet rs) {
        throw new UnsupportedOperationException("No futuro vai ser implementado o método");
    }

    @Override
    protected Genero toFromModel(ResultSet resultSet) throws SQLException {
        return Genero.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
