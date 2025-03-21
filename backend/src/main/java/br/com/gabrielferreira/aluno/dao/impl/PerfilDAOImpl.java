package br.com.gabrielferreira.aluno.dao.impl;

import br.com.gabrielferreira.aluno.dao.PerfilDAO;
import br.com.gabrielferreira.aluno.model.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.aluno.dao.factory.PerfilDAOFactory.toFromModel;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;
import static br.com.gabrielferreira.aluno.utils.dao.PerfilEnumDao.FIND_BY_CODIGO_SQL;
import static br.com.gabrielferreira.aluno.utils.dao.PerfilEnumDao.FIND_BY_ID_SQL;

@Getter
@AllArgsConstructor
public class PerfilDAOImpl implements PerfilDAO {

    private Connection connection;

    @Override
    public Perfil buscarPorId(Long id) throws SQLException {
        Perfil perfil = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL.getSql())) {
            // Setando o id na consulta
            preparedStatement.setLong(1, id);

            // Executar a consulta
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    perfil = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            gerarLogWarn("Erro ao buscar perfil por id : {}", e);
            throw new SQLException(e.getMessage());
        }
        return perfil;
    }

    @Override
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
            gerarLogWarn("Erro ao buscar perfil por código : {}", e);
            throw new SQLException(e.getMessage());
        }

        return perfil;
    }
}
