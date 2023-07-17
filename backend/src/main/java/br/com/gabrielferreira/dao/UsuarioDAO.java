package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO extends GenericoDAO<Usuario>{

    protected UsuarioDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected String insertSQL() {
        return "INSERT INTO USUARIO (NOME, EMAIL, SENHA) VALUES (?, ?, ?)";
    }

    @Override
    protected String findByIdSQL() {
        return "SELECT U.ID as ID, U.NOME as NOME, U.EMAIL as EMAIL from USUARIO U WHERE U.ID = ?";
    }

    @Override
    protected String deleteByIdSQL() {
        return "DELETE FROM USUARIO WHERE ID = ?";
    }

    @Override
    protected String updateSQL() {
        return "UPDATE USUARIO SET NOME = ? WHERE ID = ?";
    }

    @Override
    protected void toInsert(Usuario entidade, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getEmail());
        preparedStatement.setString(3, entidade.getSenha());
    }

    @Override
    protected void toUpdate(Usuario entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setLong(2, id);
    }

    @Override
    protected Usuario toFromModel(ResultSet resultSet) throws SQLException {
        return Usuario.builder()
                .id(resultSet.getLong("ID"))
                .nome(resultSet.getString("NOME"))
                .email(resultSet.getString("EMAIL"))
                .build();
    }

    @Override
    protected void toIdEntity(Usuario entidade, ResultSet rs) throws SQLException {
        entidade.setId(rs.getLong(1));
    }
}
