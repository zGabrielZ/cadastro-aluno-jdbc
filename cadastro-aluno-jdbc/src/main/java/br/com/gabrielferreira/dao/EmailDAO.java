package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.modelo.Email;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.*;

@Slf4j
public class EmailDAO implements Serializable {

    private static final long serialVersionUID = -2611224777912930201L;

    private final transient Connection connection;

    public EmailDAO(){
        connection = ConexaoBD.getConnection();
    }

    public Email inserir(Email email) throws SQLException {
        String sql = "INSERT INTO EMAIL (ENDERECO) VALUES (?)";
        try(PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Fazer a inserção do email
            st.setString(1, email.getEndereco());

            // Executar essa inserção do email
            st.execute();

            // Obter o id do email
            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()) {
                    email.setId(rs.getLong(1));
                }
            }

            // Salvar no banco de dados
            connection.commit();
        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
            connection.rollback();
            log.info("Rollback realizado !");
        }
        return email;
    }

    public boolean checarEmailCadastrado(String endereco){
        String sql = "SELECT * FROM EMAIL WHERE ENDERECO = ?";
        try(PreparedStatement st = connection.prepareStatement(sql)){
            // Realizar a consulta
            st.setString(1, endereco);

            try(ResultSet rs = st.executeQuery()){
                return rs.next();
            }

        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
        }
        return false;
    }
}
