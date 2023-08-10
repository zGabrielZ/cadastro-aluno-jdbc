package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.gabrielferreira.utils.dao.TipoTelefoneEnumDao.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

@AllArgsConstructor
public class TipoTelefoneDAO {

    @Getter
    private final Connection connection;

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

    private TipoTelefone toFromModel(ResultSet resultSet) throws SQLException{
        return TipoTelefone.builder()
                .id(resultSet.getLong("ID"))
                .descricao(resultSet.getString("DESCRICAO"))
                .codigo(resultSet.getString("CODIGO"))
                .build();
    }
}
