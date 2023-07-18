package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioDAO extends GenericoDAO<Usuario>{

    protected UsuarioDAO(Connection connection) {
        super(connection);
        super.insertSQL = "INSERT INTO USUARIO (NOME, EMAIL, SENHA, DATA_NASCIMENTO, CPF) VALUES (?, ?, ?, ?, ?)";
        super.findByIdSQL = "SELECT U.ID as ID, U.NOME as NOME, U.EMAIL as EMAIL, U.SENHA AS SENHA, U.DATA_NASCIMENTO as DATA_NASCIMENTO, U.CPF as CPF from USUARIO U WHERE U.ID = ?";
        super.deleteByIdSQL = "DELETE FROM USUARIO WHERE ID = ?";
        super.updateSQL = "UPDATE USUARIO SET NOME = ?, EMAIL = ?, SENHA = ?, DATA_NASCIMENTO = ?, CPF = ? WHERE ID = ?";
    }

    @Override
    protected void toInsertOrUpdate(Usuario entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getEmail());
        preparedStatement.setString(3, entidade.getSenha());
        preparedStatement.setObject(4, entidade.getDataNascimento());
        preparedStatement.setString(5, entidade.getCpf());

        if(id != null){
            preparedStatement.setLong(6, id);
        }
    }

    @Override
    protected void toIdEntityInsert(Usuario entidade, ResultSet rs) throws SQLException {
        entidade.setId(rs.getLong(1));
    }

    @Override
    protected Usuario toFromModel(ResultSet resultSet) throws SQLException {
        return Usuario.builder()
                .id(resultSet.getLong("ID"))
                .nome(resultSet.getString("NOME"))
                .email(resultSet.getString("EMAIL"))
                .senha("SENHA")
                .dataNascimento(toLocalDateDataNascimento(resultSet.getObject("DATA_NASCIMENTO", LocalDate.class)))
                .cpf(resultSet.getString("CPF"))
                .build();
    }

    private LocalDate toLocalDateDataNascimento(Object dataNascimento){
        if(dataNascimento instanceof LocalDate dataConvertida){
            return dataConvertida;
        }
        return null;
    }
}
