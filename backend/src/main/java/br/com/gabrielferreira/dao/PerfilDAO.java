package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.modelo.Perfil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.PerfilEnumDao.FIND_BY_ID_SQL;


public class PerfilDAO extends GenericoDAO<Perfil>{

    protected PerfilDAO(Connection connection) {
        super(connection);
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
    }

    @Override
    protected void toInsertOrUpdate(Perfil entidade, Long id, PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException("No futuro vai ser implementado o método");
    }

    @Override
    protected void toIdEntityInsert(Perfil entidade, ResultSet rs) {
        throw new UnsupportedOperationException("No futuro vai ser implementado o método");
    }

    @Override
    protected Perfil toFromModel(ResultSet resultSet) throws SQLException {
        return Perfil.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
