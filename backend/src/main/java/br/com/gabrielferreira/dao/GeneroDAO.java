package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Genero;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.GeneroEnumDao.*;

@Slf4j
public class GeneroDAO extends GenericoDAO<Genero>{

    protected GeneroDAO(Connection connection) {
        super(connection);
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
    }

    public Genero buscarGeneroPorCodigo(String codigo) throws SQLException {
        Genero genero = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_CODIGO_SQL.getSql())) {
            preparedStatement.setString(1, codigo);
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    genero = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            log.warn("Erro ao buscar gênero por código : {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return genero;
    }

    @Override
    protected void toInsertOrUpdate(Genero entidade, Long id, PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void toIdEntityInsert(Genero entidade, ResultSet rs) {
        throw new UnsupportedOperationException();
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
