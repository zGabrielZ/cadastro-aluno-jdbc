package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.modelo.Aluno;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.*;

@Slf4j
public class AlunoDAO implements Serializable {

    private static final long serialVersionUID = -2611224777912930201L;

    private final transient Connection connection;

    public AlunoDAO(){
        connection = ConexaoBD.getConnection();
    }

    public Aluno inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO ALUNO (NOME, CPF, EMAIL_ID) VALUES (?, ?, ?)";
        try(PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // Fazer a inserção do Aluno
            st.setString(1, aluno.getNome());
            st.setString(2, aluno.getCpf());
            st.setLong(3, aluno.getEmail().getId());

            // Executar essa inserção do aluno
            st.execute();

            // Obter o id do Aluno
            try(ResultSet rs = st.getGeneratedKeys()){
                if(rs.next()) {
                    aluno.setId(rs.getLong(1));
                }
            }

            // Salvar no banco de dados
            connection.commit();
        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
            connection.rollback();
            log.info("Rollback realizado !");
        }
        return aluno;
    }
}
