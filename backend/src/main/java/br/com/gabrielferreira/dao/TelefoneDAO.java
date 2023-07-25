package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.modelo.Telefone;
import lombok.Getter;

import java.sql.*;
import static br.com.gabrielferreira.utils.dao.TelefoneEnumDao.*;

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

    private void toInsertOrUpdate(Telefone entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entidade.getDdd());
        preparedStatement.setString(2, entidade.getNumero());
        preparedStatement.setLong(3, entidade.getUsuario().getId());
        preparedStatement.setObject(4, entidade.getTipoTelefone().getId());

        if(id != null){
            preparedStatement.setLong(5, id);
        }
    }
}
