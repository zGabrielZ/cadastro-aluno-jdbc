package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.modelo.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.utils.dao.TelefoneEnumDao.*;

@Slf4j
public class TelefoneDAO {

    @Getter
    private final Connection connection;

    public TelefoneDAO(Connection connection){
        this.connection = connection;
    }

    public void inserirTelefone(Telefone telefone) throws SQLException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_SQL.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            toInsertOrUpdate(telefone, null, preparedStatement);

            // Executar essa inserção
            preparedStatement.execute();

            // Obter o id do registro salvo
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                while (rs.next()) {
                    telefone.setId(rs.getLong(1));
                }
            }
        }
    }

    public List<Telefone> buscarTelefonesPorIdUsuario(Long idUsuario) throws SQLException {
        List<Telefone> telefones = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_USUARIO_SQL.getSql())) {
            // Setando o id na consulta
            preparedStatement.setLong(1, idUsuario);

            // Executar a consulta
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    Telefone telefone = toFromModel(resultSet);
                    telefones.add(telefone);
                }
            }

        } catch (SQLException e){
            log.warn("Erro ao buscar telefones por id usuário : {}", e.getMessage());
            throw new SQLException(e.getMessage());
        }
        return telefones;
    }

    private void toInsertOrUpdate(Telefone entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getDdd());
        preparedStatement.setString(2, entidade.getNumero());
        preparedStatement.setLong(3, entidade.getUsuario().getId());
        preparedStatement.setObject(4, entidade.getTipoTelefone().getId());

        if(id != null){
            preparedStatement.setLong(5, id);
        }
    }

    private Telefone toFromModel(ResultSet resultSet) throws SQLException {
        TipoTelefone tipoTelefone = toTipoTelefone(resultSet);

        return Telefone.builder()
                .id(resultSet.getLong("ID"))
                .ddd(resultSet.getString("DDD"))
                .numero(resultSet.getString("NUMERO"))
                .tipoTelefone(tipoTelefone)
                .build();
    }

    private TipoTelefone toTipoTelefone(ResultSet resultSet) throws SQLException {
        return TipoTelefone.builder()
                .id(resultSet.getLong("ID_TIPO_TELEFONE"))
                .descricao(resultSet.getString("DESCRICAO_TIPO_TELEFONE"))
                .codigo(resultSet.getString("CODIGO_TIPO_TELEFONE"))
                .build();
    }
}
