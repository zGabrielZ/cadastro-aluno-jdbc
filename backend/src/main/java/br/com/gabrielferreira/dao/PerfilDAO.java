package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.modelo.Perfil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.PerfilEnumDao.*;

@Slf4j
public class PerfilDAO extends GenericoDAO<Perfil>{

    protected PerfilDAO(Connection connection) {
        super(connection);
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
    }

    public Perfil buscarPerfilPorCodigo(String codigo) throws SQLException {
        Perfil perfil = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_CODIGO_SQL.getSql())) {
            preparedStatement.setString(1, codigo);
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    perfil = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            log.warn("Erro ao buscar perfil por código : {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }

        return perfil;
    }

    @Override
    protected void toInsertOrUpdate(Perfil entidade, Long id, PreparedStatement preparedStatement) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void toIdEntityInsert(Perfil entidade, ResultSet rs) {
        throw new UnsupportedOperationException();
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
