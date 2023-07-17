package br.com.gabrielferreira.dao.v2;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.*;

@Slf4j
public abstract class GenericoDAO<T> implements Serializable {

    private static final long serialVersionUID = -2611224777912930201L;

    private final transient Connection connection;

    GenericoDAO(Connection connection){
        this.connection = connection;
    }

    public void inserir(T entidade) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertSQL(), Statement.RETURN_GENERATED_KEYS)) {
            toInsert(entidade, preparedStatement);

            // Executar essa inserção do aluno
            preparedStatement.execute();

            // Obter o id da entidade
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                if(rs.next()) {
                    toIdEntity(entidade, rs);
                }
            }

            // Salvar no banco de dados
            connection.commit();
        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
            connection.rollback();
            log.info("Rollback realizado !");
        }
    }

    protected abstract String insertSQL();

    protected abstract void toInsert(T entidade, PreparedStatement preparedStatement) throws SQLException;

    protected abstract void toIdEntity(T entidade, ResultSet rs) throws SQLException;
}
