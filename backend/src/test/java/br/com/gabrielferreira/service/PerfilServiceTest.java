package br.com.gabrielferreira.service;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.conexao.config.ConfigBandoDeDadosTestImpl;
import br.com.gabrielferreira.dao.PerfilDAO;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.Perfil;
import br.com.gabrielferreira.dto.view.PerfilViewDTO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerfilServiceTest {

    private PerfilService perfilService;

    @BeforeEach
    public void criarInstancias(){
        ConexaoBD conexaoBD = new ConexaoBD(new ConfigBandoDeDadosTestImpl());
        PerfilDAO perfilDAO = new PerfilDAO(conexaoBD.getConnection());
        perfilService = new PerfilService(perfilDAO);
    }

    @Test
    @DisplayName("Deve encontrar perfil por id")
    @Order(1)
    void deveEncontrarPerfilPorId(){
        PerfilViewDTO perfilEncontrado = perfilService.buscarPorId(1L);

        assertNotNull(perfilEncontrado.getId());
        assertEquals("Administrador", perfilEncontrado.getDescricao());
        assertEquals("ADMINISTRADOR", perfilEncontrado.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil por id")
    @Order(2)
    void naoDeveEncontrarPerfilPorId(){
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPorId(-1L));
    }

    @Test
    @DisplayName("Deve encontrar perfil quando informar o código")
    @Order(3)
    void deveEncontrarGeneroPorCodigo(){
        Perfil perfil = perfilService.buscarPerfilPorCodigo("ADMINISTRADOR");

        assertNotNull(perfil.getId());
        assertEquals("Administrador", perfil.getDescricao());
        assertEquals("ADMINISTRADOR", perfil.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil quando informar código errado")
    @Order(4)
    void naoDeveEncontrarPerfilPorCodigo(){
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPerfilPorCodigo("TESTE"));
    }
}
