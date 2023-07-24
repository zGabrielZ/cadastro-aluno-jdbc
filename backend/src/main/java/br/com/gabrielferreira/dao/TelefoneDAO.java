package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.modelo.Telefone;

import java.sql.*;

import static br.com.gabrielferreira.utils.dao.TelefoneEnumDao.*;

public class TelefoneDAO extends GenericoDAO<Telefone>{

    protected TelefoneDAO(Connection connection) {
        super(connection);
        super.insertSQL = INSERT_SQL.getSql();
    }

    public void inserirTelefone(Telefone telefone) throws SQLException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            toInsertOrUpdate(telefone, null, preparedStatement);

            // Executar essa inserção
            preparedStatement.execute();

            // Obter o id do registro salvo
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                while (rs.next()) {
                    toIdEntityInsert(telefone, rs);
                }
            }
        }
    }

    @Override
    protected void toInsertOrUpdate(Telefone entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getDdd());
        preparedStatement.setString(2, entidade.getNumero());
        preparedStatement.setLong(3, entidade.getUsuario().getId());
        preparedStatement.setObject(4, entidade.getTipoTelefone().getId());

        if(id != null){
            preparedStatement.setLong(5, id);
        }
    }

    @Override
    protected void toIdEntityInsert(Telefone entidade, ResultSet rs) throws SQLException {
        entidade.setId(rs.getLong(1));
    }

    @Override
    protected Telefone toFromModel(ResultSet resultSet) {
        throw new UnsupportedOperationException();
    }
}
