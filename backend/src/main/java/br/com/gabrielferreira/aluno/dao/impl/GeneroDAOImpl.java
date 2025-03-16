package br.com.gabrielferreira.aluno.dao.impl;

import br.com.gabrielferreira.aluno.dao.GeneroDAO;
import br.com.gabrielferreira.aluno.model.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.aluno.dao.factory.GeneroDAOFactory.toFromModel;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;
import static br.com.gabrielferreira.aluno.utils.dao.GeneroEnumDao.FIND_BY_CODIGO_SQL;
import static br.com.gabrielferreira.aluno.utils.dao.GeneroEnumDao.FIND_BY_ID_SQL;

@Getter
@AllArgsConstructor
public class GeneroDAOImpl implements GeneroDAO {

    private Connection connection;

    @Override
    public Genero buscarPorId(Long id) throws SQLException {
        Genero genero = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL.getSql())) {
            // Setando o id na consulta
            preparedStatement.setLong(1, id);

            // Executar a consulta
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    genero = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            gerarLogWarn("Erro ao buscar gênero por id : {}", e);
            throw new SQLException(e.getMessage());
        }
        return genero;
    }

    @Override
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
            gerarLogWarn("Erro ao buscar gênero por código : {}", e);
            throw new SQLException(e.getMessage());
        }

        return genero;
    }
}
