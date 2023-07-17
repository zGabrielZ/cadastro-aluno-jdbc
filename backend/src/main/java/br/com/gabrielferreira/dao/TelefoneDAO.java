//package br.com.gabrielferreira.dao;
//
//import br.com.gabrielferreira.conexao.ConexaoBD;
//import br.com.gabrielferreira.modelo.Telefone;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.Serializable;
//import java.sql.*;
//
//@Slf4j
//public class TelefoneDAO implements Serializable {
//
//    private static final long serialVersionUID = -2611224777912930201L;
//
//    private final transient Connection connection;
//
//    public TelefoneDAO(){
//        connection = ConexaoBD.getConnection();
//    }
//
//    public Telefone inserir(Telefone telefone) throws SQLException {
//        String sql = "INSERT INTO TELEFONE (NUMERO, ID_ALUNO, ID_TIPO, DDD) VALUES (?, ?, ?, ?)";
//        try(PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
//            // Fazer a inserção do Telefone
//            st.setString(1, telefone.getNumero());
//            st.setLong(2, telefone.getAluno().getId());
//            st.setLong(3, telefone.getTipoTelefone().getId());
//            st.setString(4, telefone.getDdd());
//
//            // Executar essa inserção do telefone
//            st.execute();
//
//            // Obter o id do Telefone
//            try(ResultSet rs = st.getGeneratedKeys()){
//                if(rs.next()) {
//                    telefone.setId(rs.getLong(1));
//                }
//            }
//
//            // Salvar no banco de dados
//            connection.commit();
//        } catch (Exception e){
//            log.warn("Erro : {}",e.getMessage());
//            connection.rollback();
//            log.info("Rollback realizado !");
//        }
//        return telefone;
//    }
//}
