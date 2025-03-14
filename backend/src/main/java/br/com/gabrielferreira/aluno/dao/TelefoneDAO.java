package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.aluno.dao.factory.TelefoneDAOFactory.toFromModel;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogInfo;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;
import static br.com.gabrielferreira.aluno.utils.dao.TelefoneEnumDao.*;

@Getter
@AllArgsConstructor
public class TelefoneDAO {

    private Connection connection;

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
            gerarLogWarn("Erro ao buscar telefones por id usuário : {}", e);
            throw new SQLException(e.getMessage());
        }
        return telefones;
    }

    public void deleteTudo() throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL.getSql())){
            // Executar a consulta do delete
            preparedStatement.executeUpdate();

            // Salvar no banco de dados
            connection.commit();
        } catch (SQLException e){
            gerarLogWarn("Erro ao deletar tudo do telefone : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
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

    private void gerarRollback() throws SQLException {
        try {
            connection.rollback();
            gerarLogInfo("Rollback do telefone realizado !");
        } catch (SQLException e){
            gerarLogWarn("Ocorreu erro ao gerar o rollback, {}", e);
            throw new SQLException(e.getMessage());
        }
    }
}
