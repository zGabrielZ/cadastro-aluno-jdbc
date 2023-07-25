package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Genero;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.GeneroEnumDao.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

public class GeneroDAO {

    @Getter
    private final Connection connection;

    public GeneroDAO(Connection connection) {
        this.connection = connection;
    }

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

    private Genero toFromModel(ResultSet resultSet) throws SQLException {
        return Genero.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
