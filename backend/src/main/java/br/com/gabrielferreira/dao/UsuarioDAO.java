package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Usuario;

import java.sql.*;
import java.time.LocalDate;

import static br.com.gabrielferreira.utils.dao.UsuarioEnumDao.*;


public class UsuarioDAO extends GenericoDAO<Usuario>{

    protected UsuarioDAO(Connection connection) {
        super(connection);
        super.insertSQL = INSERT_SQL.getSql();
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
        super.deleteByIdSQL = DELETE_BY_ID_SQL.getSql();
        super.updateSQL = UPDAYE_BY_ID_SQL.getSql();
    }

    @Override
    protected void toInsertOrUpdate(Usuario entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getEmail());
        preparedStatement.setString(3, entidade.getSenha());
        preparedStatement.setObject(4, entidade.getDataNascimento());
        preparedStatement.setString(5, entidade.getCpf());
        preparedStatement.setString(6, entidade.getNomeSocial());

        if(entidade.getGenero() != null){
            preparedStatement.setLong(7, entidade.getGenero().getId());
        } else {
            preparedStatement.setNull(7, Types.INTEGER);
        }

        preparedStatement.setLong(8, entidade.getPerfil().getId());

        if(id != null){
            preparedStatement.setLong(9, id);
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
                .nomeSocial(resultSet.getString("NOME_SOCIAL"))
                .genero(toGenero(resultSet))
                .build();
    }

    private LocalDate toLocalDateDataNascimento(Object dataNascimento){
        if(dataNascimento instanceof LocalDate dataConvertida){
            return dataConvertida;
        }
        return null;
    }

    private Genero toGenero(ResultSet resultSet) throws SQLException{
        long idGenero = resultSet.getLong("ID_GENERO");
        if(idGenero != 0){
            return Genero.builder()
                    .id(idGenero)
                    .descricao(resultSet.getString("DESCRICAO_GENERO"))
                    .codigo(resultSet.getString("CODIGO_GENERO"))
                    .build();
        }
        return null;
    }
}
