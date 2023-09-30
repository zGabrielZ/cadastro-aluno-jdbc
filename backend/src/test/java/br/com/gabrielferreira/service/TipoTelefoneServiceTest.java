package br.com.gabrielferreira.service;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.conexao.config.ConfigBandoDeDadosTestImpl;
import br.com.gabrielferreira.dao.TipoTelefoneDAO;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.TipoTelefone;
import br.com.gabrielferreira.model.dto.TipoTelefoneViewDTO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TipoTelefoneServiceTest {

    private TipoTelefoneService tipoTelefoneService;

    @BeforeEach
    public void criarInstancias(){
        ConexaoBD conexaoBD = new ConexaoBD(new ConfigBandoDeDadosTestImpl());
        TipoTelefoneDAO tipoTelefoneDAO = new TipoTelefoneDAO(conexaoBD.getConnection());
        tipoTelefoneService = new TipoTelefoneService(tipoTelefoneDAO);
    }

    @Test
    @DisplayName("Deve encontrar tipo de telefone quando informar o código")
    @Order(1)
    void deveEncontrarTipoTelefonePorCodigo(){
        TipoTelefone tipoTelefone = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");

        assertNotNull(tipoTelefone.getId());
        assertEquals("Residencial", tipoTelefone.getDescricao());
        assertEquals("RESIDENCIAL", tipoTelefone.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar tipo de telefone quando informar código errado")
    @Order(2)
    void naoDeveEncontrarGenero(){
        assertThrows(RegistroNaoEncontradoException.class, () -> tipoTelefoneService.buscarTipoTelefonePorCodigo("TESTE"));
    }

    @Test
    @DisplayName("Deve encontrar tipo telefone por id")
    @Order(3)
    void deveEncontrarTipoTelefonePorId(){
        TipoTelefoneViewDTO tipoTelefoneViewDTO = tipoTelefoneService.buscarPorId(1L);

        assertNotNull(tipoTelefoneViewDTO.getId());
        assertEquals("Residencial", tipoTelefoneViewDTO.getDescricao());
        assertEquals("RESIDENCIAL", tipoTelefoneViewDTO.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil por id")
    @Order(4)
    void naoDeveEncontrarPerfilPorId(){
        assertThrows(RegistroNaoEncontradoException.class, () -> tipoTelefoneService.buscarPorId(-1L));
    }
}
