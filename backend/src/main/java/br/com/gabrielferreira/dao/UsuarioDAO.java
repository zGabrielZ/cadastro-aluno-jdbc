package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.Usuario;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static br.com.gabrielferreira.utils.dao.UsuarioEnumDao.*;


@Slf4j
public class UsuarioDAO extends GenericoDAO<Usuario>{

    private final TelefoneDAO telefoneDAO;

    protected UsuarioDAO(Connection connection, TelefoneDAO telefoneDAO) {
        super(connection);
        this.telefoneDAO = telefoneDAO;
        super.insertSQL = INSERT_SQL.getSql();
        super.findByIdSQL = FIND_BY_ID_SQL.getSql();
        super.deleteByIdSQL = DELETE_BY_ID_SQL.getSql();
        super.updateSQL = UPDAYE_BY_ID_SQL.getSql();
    }

    public void inserirUsuarioComTelefones(Usuario usuario, List<Telefone> telefones) throws SQLException {
        try {
            inserirUsuario(usuario);

            for (Telefone telefone : telefones) {
                telefone.setUsuario(usuario);
                telefoneDAO.inserirTelefone(telefone);
            }
        } catch (SQLException e){
            log.warn("Erro ao salvar usuário e telefone : {}",e.getMessage());
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    private void inserirUsuario(Usuario usuario) throws SQLException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            toInsertOrUpdate(usuario, null, preparedStatement);

            // Executar essa inserção
            preparedStatement.execute();

            // Obter o id do registro salvo
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                while (rs.next()) {
                    toIdEntityInsert(usuario, rs);
                }
            }
        }
    }

    public void deletarTelefonesPorIdUsuario(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_TELEFONE_BY_ID_SQL.getSql())){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            getConnection().commit();
        } catch (Exception e){
            log.warn("Erro ao deletar o telefone do usuário : {}",e.getMessage());
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
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
