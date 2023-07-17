package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.sql.*;

@Slf4j
public abstract class GenericoDAO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2611224777912930201L;

    private static final String MSG_ROLLBACK = "Rollback realizado !";

    private final transient Connection connection;

    protected String insertSQL;

    protected String findByIdSQL;

    protected String deleteByIdSQL;

    protected String updateSQL;

    protected GenericoDAO(Connection connection){
        this.connection = connection;
    }

    public void inserir(T entidade) throws SQLException {
        validarSql(insertSQL, "É necessário informar o sql do insert");

        try(PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            toInsertOrUpdate(entidade, null, preparedStatement);

            // Executar essa inserção
            preparedStatement.execute();

            // Obter o id do registro salvo
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                while (rs.next()) {
                    toIdEntityInsert(entidade, rs);
                }
            }

            // Salvar no banco de dados
            connection.commit();
        } catch (Exception e){
            log.warn("Erro ao salvar registro : {}",e.getMessage());
            connection.rollback();
            log.info(MSG_ROLLBACK);
        }
    }

    public T buscarPorId(Long id) {
        validarSql(findByIdSQL, "É necessário informar o sql do buscar por id");

        T entidade = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(findByIdSQL)) {
            // Setando o id na consulta
            preparedStatement.setLong(1, id);

            // Executar a consulta
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    entidade = toFromModel(resultSet);
                }
            }

        } catch (Exception e){
            log.warn("Erro ao buscar registro por id : {}", e.getMessage());
        }
        return entidade;
    }

    public void atualizar(T entidade, Long id) throws SQLException {
        validarSql(updateSQL, "É necessário informar o sql do update por id");

        try(PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            toInsertOrUpdate(entidade, id, preparedStatement);

            // Executar essa atualização
            preparedStatement.executeUpdate();

            // Salvar no banco de dados
            connection.commit();
        } catch (Exception e){
            log.warn("Erro ao atualizar registro : {}",e.getMessage());
            connection.rollback();
            log.info(MSG_ROLLBACK);
        }
    }

    public void deletarPorId(Long id) throws SQLException {
        validarSql(deleteByIdSQL, "É necessário informar o sql do delete por id");

        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdSQL)){

            // Setando o id na consulta delete
            preparedStatement.setLong(1, id);

            // Executar a consulta do delete
            preparedStatement.executeUpdate();

            // Salvar no banco de dados
            connection.commit();

        } catch (Exception e){
            log.warn("Erro ao deletar registro : {}",e.getMessage());
            connection.rollback();
            log.info(MSG_ROLLBACK);
        }
    }

    private void validarSql(String sql, String msg){
        if (sql == null || sql.isBlank()){
            throw new RegraDeNegocioException(msg);
        }
    }

    protected abstract void toInsertOrUpdate(T entidade, Long id, PreparedStatement preparedStatement) throws SQLException;

    protected abstract T toFromModel(ResultSet resultSet) throws SQLException;

    protected abstract void toIdEntityInsert(T entidade, ResultSet rs) throws SQLException;
}
