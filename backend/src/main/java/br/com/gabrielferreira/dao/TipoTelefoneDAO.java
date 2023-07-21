package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.TipoTelefone;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.TipoTelefoneEnumDao.*;

@Slf4j
public class TipoTelefoneDAO extends GenericoDAO<TipoTelefone>{

    protected TipoTelefoneDAO(Connection connection) {
        super(connection);
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
    }

    public TipoTelefone buscarTipoTelefonePorCodigo(String codigo) throws SQLException {
        TipoTelefone tipoTelefone = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_CODIGO_SQL.getSql())) {
            preparedStatement.setString(1, codigo);
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    tipoTelefone = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            log.warn("Erro ao buscar tipo telefone por código : {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return tipoTelefone;
    }

    @Override
    protected void toInsertOrUpdate(TipoTelefone entidade, Long id, PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void toIdEntityInsert(TipoTelefone entidade, ResultSet rs) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TipoTelefone toFromModel(ResultSet resultSet) throws SQLException{
        return TipoTelefone.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
