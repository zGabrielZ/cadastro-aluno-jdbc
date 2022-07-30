package br.com.gabrielferreira.dao;
import br.com.gabrielferreira.conexao.ConexaoBDTest;
import br.com.gabrielferreira.modelo.TipoTelefone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

class TipoTelefoneDAOTest {

    private TipoTelefoneDAO tipoTelefoneDAO;

    @BeforeEach
    public void criarInstanciasTipoTelefone(){
        Connection connection = ConexaoBDTest.getConnection();
        tipoTelefoneDAO = new TipoTelefoneDAO(connection);
    }

    @Test
    @DisplayName("Deveria buscar uma lista de tipo telefones.")
    void deveBuscarListaTipoTelefones(){
        // Cenário e executando
        List<TipoTelefone> tipoTelefones = tipoTelefoneDAO.listaDeTipoTelefones();

        // Verficando
        assertFalse(tipoTelefones.isEmpty());
    }

    @Test
    @DisplayName("Deveria buscar um tipo de telefone por id.")
    void deveBuscarTipoTelefonePorId(){
        // Cenário e executando
        TipoTelefone tipoTelefone = tipoTelefoneDAO.buscarPorId(1L);

        // Verficando
        assertNotNull(tipoTelefone);
    }

}
