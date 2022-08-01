package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.modelo.TipoTelefone;
import static br.com.gabrielferreira.utils.dao.TipoTelefoneEnumDaoUtils.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TipoTelefoneDAO implements Serializable {

    private static final long serialVersionUID = 4476082906382028143L;
    private final transient Connection connection;

    public TipoTelefoneDAO(){
        this.connection = ConexaoBD.getConnection();
    }

    public List<TipoTelefone> listaDeTipoTelefones(){
        List<TipoTelefone> tipoTelefones = new ArrayList<>();
        String sql = "SELECT ID, DESCRICAO FROM TIPO_TELEFONE";
        try(PreparedStatement st = connection.prepareStatement(sql)){

            // Realizar a consulta
            try(ResultSet rs = st.executeQuery()){
                while (rs.next()){

                    TipoTelefone tipoTelefone = new TipoTelefone();
                    tipoTelefone.setId(rs.getLong(ID.getValor()));
                    tipoTelefone.setDescricao(rs.getString(DESCRICAO.getValor()));

                    tipoTelefones.add(tipoTelefone);
                }
            }

        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
        }
        return tipoTelefones;
    }

    public TipoTelefone buscarPorId(Long id){
        TipoTelefone tipoTelefone = new TipoTelefone();
        String sql = "SELECT ID, DESCRICAO FROM TIPO_TELEFONE WHERE ID = ?";
        try(PreparedStatement st = connection.prepareStatement(sql)){

            // Realizar a consulta
            st.setLong(1,id);
            try(ResultSet rs = st.executeQuery()){
                while (rs.next()){

                    tipoTelefone.setId(rs.getLong(ID.getValor()));
                    tipoTelefone.setDescricao(rs.getString(DESCRICAO.getValor()));

                }
            }

        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
        }
        return tipoTelefone;
    }

}
