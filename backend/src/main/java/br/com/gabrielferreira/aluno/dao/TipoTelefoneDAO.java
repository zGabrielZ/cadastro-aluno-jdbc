package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.aluno.dao.factory.TipoTelefoneDAOFactory.toFromModel;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;
import static br.com.gabrielferreira.aluno.utils.dao.TipoTelefoneEnumDao.FIND_BY_CODIGO_SQL;
import static br.com.gabrielferreira.aluno.utils.dao.TipoTelefoneEnumDao.FIND_BY_ID_SQL;

@Getter
@AllArgsConstructor
public class TipoTelefoneDAO {

    private Connection connection;

    public TipoTelefone buscarPorId(Long id) throws SQLException {
        TipoTelefone tipoTelefone = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL.getSql())) {
            // Setando o id na consulta
            preparedStatement.setLong(1, id);

            // Executar a consulta
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    tipoTelefone = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            gerarLogWarn("Erro ao buscar tipo de telefone por id : {}", e);
            throw new SQLException(e.getMessage());
        }
        return tipoTelefone;
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
            gerarLogWarn("Erro ao buscar tipo telefone por código : {}", e);
            throw new SQLException(e.getMessage());
        }

        return tipoTelefone;
    }
}
