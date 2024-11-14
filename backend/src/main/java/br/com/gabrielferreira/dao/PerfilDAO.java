package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.model.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.PerfilEnumDao.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

@Getter
@AllArgsConstructor
public class PerfilDAO {

    private Connection connection;

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

    private Perfil toFromModel(ResultSet resultSet) throws SQLException {
        return Perfil.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
