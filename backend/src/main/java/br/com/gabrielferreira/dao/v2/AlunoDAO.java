package br.com.gabrielferreira.dao.v2;

import br.com.gabrielferreira.modelo.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO extends GenericoDAO<Aluno> {

    public AlunoDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected void toInsert(Aluno entidade, PreparedStatement preparedStatement) throws SQLException {
        // Fazer a inserção do Aluno
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getCpf());
        preparedStatement.setLong(3, entidade.getEmail().getId());
    }

    @Override
    protected void toIdEntity(Aluno entidade, ResultSet rs) throws SQLException{
        entidade.setId(rs.getLong(1));
    }

    @Override
    protected String insertSQL() {
        return "INSERT INTO ALUNO (NOME, CPF, EMAIL_ID) VALUES (?, ?, ?)";
    }
}
