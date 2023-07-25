package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Perfil;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.Usuario;
import lombok.Getter;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static br.com.gabrielferreira.utils.dao.UsuarioEnumDao.*;
import static br.com.gabrielferreira.utils.LogUtils.*;


public class UsuarioDAO {

    @Getter
    private final Connection connection;

    private final TelefoneDAO telefoneDAO;

    public UsuarioDAO(Connection connection, TelefoneDAO telefoneDAO){
        this.connection = connection;
        this.telefoneDAO = telefoneDAO;
    }

    public void inserir(Usuario usuario) throws SQLException {
        try {
            inserirUsuario(usuario);

            // Salvar no banco de dados
            connection.commit();
        } catch (SQLException e){
            gerarLogWarn("Erro ao salvar usuário : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    public void inserirUsuarioComTelefones(Usuario usuario, List<Telefone> telefones) throws SQLException {
        try {
            inserirUsuario(usuario);

            for (Telefone telefone : telefones) {
                telefone.setUsuario(usuario);
                telefoneDAO.inserirTelefone(telefone);
            }

            // Salvar no banco de dados
            connection.commit();
        } catch (SQLException e){
            gerarLogWarn("Erro ao salvar usuário e telefone : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    public Usuario buscarPorId(Long id) throws SQLException {
        Usuario usuario = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL.getSql())) {
            // Setando o id na consulta
            preparedStatement.setLong(1, id);

            // Executar a consulta
            preparedStatement.execute();

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    usuario = toFromModel(resultSet);
                }
            }

        } catch (SQLException e){
            gerarLogWarn("Erro ao buscar usuário por id : {}", e);
            throw new SQLException(e.getMessage());
        }
        return usuario;
    }

    public void atualizar(Usuario usuario, Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDAYE_BY_ID_SQL.getSql())) {
            toInsertOrUpdate(usuario, id, preparedStatement);

            // Executar essa atualização
            preparedStatement.executeUpdate();

            // Salvar no banco de dados
            connection.commit();
        } catch (Exception e){
            gerarLogWarn("Erro ao atualizar usuário : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    public void deletarPorId(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL.getSql())){

            // Setando o id na consulta delete
            preparedStatement.setLong(1, id);

            // Executar a consulta do delete
            preparedStatement.executeUpdate();

            // Salvar no banco de dados
            connection.commit();

        } catch (SQLException e){
            gerarLogWarn("Erro ao deletar usuário : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    public void deletarTelefonesPorIdUsuario(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_TELEFONE_BY_ID_SQL.getSql())){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            getConnection().commit();
        } catch (SQLException e){
            gerarLogWarn("Erro ao deletar o telefone do usuário : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteTudo() throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL.getSql())){
            // Executar a consulta do delete
            preparedStatement.executeUpdate();

            // Salvar no banco de dados
            connection.commit();
        } catch (SQLException e){
            gerarLogWarn("Erro ao deletar tudo do usuário : {}", e);
            gerarRollback();
            throw new SQLException(e.getMessage());
        }
    }

    private void inserirUsuario(Usuario usuario) throws SQLException {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_SQL.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            toInsertOrUpdate(usuario, null, preparedStatement);

            // Executar essa inserção
            preparedStatement.execute();

            // Obter o id do registro salvo
            try(ResultSet rs = preparedStatement.getGeneratedKeys()){
                while (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
        }
    }

    private void toInsertOrUpdate(Usuario entidade, Long id, PreparedStatement preparedStatement) throws SQLException {
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

    private Usuario toFromModel(ResultSet resultSet) throws SQLException {
        LocalDate dataNascimento = toLocalDateDataNascimento(resultSet.getObject("DATA_NASCIMENTO", LocalDate.class));
        Genero genero = toGenero(resultSet);
        Perfil perfil = toPerfil(resultSet);

        return Usuario.builder()
                .id(resultSet.getLong("ID"))
                .nome(resultSet.getString("NOME"))
                .email(resultSet.getString("EMAIL"))
                .senha(resultSet.getString("EMAIL"))
                .dataNascimento(dataNascimento)
                .cpf(resultSet.getString("CPF"))
                .nomeSocial(resultSet.getString("NOME_SOCIAL"))
                .genero(genero)
                .perfil(perfil)
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

    private Perfil toPerfil(ResultSet resultSet) throws SQLException{
        long idPerfil = resultSet.getLong("ID_PERFIL");
        if(idPerfil != 0){
            return Perfil.builder()
                    .id(idPerfil)
                    .descricao(resultSet.getString("DESCRICAO_PERFIL"))
                    .codigo(resultSet.getString("CODIGO_PERFIL"))
                    .build();
        }
        return null;
    }

    private void gerarRollback() throws SQLException {
        try {
            connection.rollback();
            gerarLogInfo("Rollback do usuário realizado !");
        } catch (SQLException e){
            gerarLogWarn("Ocorreu erro ao gerar o rollback, {}", e);
            throw new SQLException(e.getMessage());
        }
    }
}
