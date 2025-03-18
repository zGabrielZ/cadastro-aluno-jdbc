package br.com.gabrielferreira.aluno.service.impl;

import br.com.gabrielferreira.aluno.ServiceIntegration;
import br.com.gabrielferreira.aluno.dao.PerfilDAO;
import br.com.gabrielferreira.aluno.dao.factory.DaoFactory;
import br.com.gabrielferreira.aluno.dto.PerfilDTO;
import br.com.gabrielferreira.aluno.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.aluno.model.Perfil;
import br.com.gabrielferreira.aluno.service.PerfilService;
import br.com.gabrielferreira.aluno.service.factory.ServiceFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PerfilServiceImplTest extends ServiceIntegration {

    private PerfilService perfilService;

    @BeforeEach
    public void criarInstancias() {
        PerfilDAO perfilDAO = DaoFactory.criarPerfilDao();
        perfilService = ServiceFactory.criarPerfilService(perfilDAO);
    }

    @Test
    @DisplayName("Deve encontrar perfil por id")
    @Order(1)
    void deveEncontrarPerfilPorId() {
        PerfilDTO perfilEncontrado = perfilService.buscarPorId(1L);

        assertNotNull(perfilEncontrado.id());
        assertEquals("Administrador", perfilEncontrado.descricao());
        assertEquals("ADMINISTRADOR", perfilEncontrado.codigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil por id")
    @Order(2)
    void naoDeveEncontrarPerfilPorId() {
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPorId(-1L));
    }

    @Test
    @DisplayName("Deve encontrar perfil quando informar o código")
    @Order(3)
    void deveEncontrarGeneroPorCodigo() {
        Perfil perfil = perfilService.buscarPerfilPorCodigo("ADMINISTRADOR");

        assertNotNull(perfil.getId());
        assertEquals("Administrador", perfil.getDescricao());
        assertEquals("ADMINISTRADOR", perfil.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil quando informar código errado")
    @Order(4)
    void naoDeveEncontrarPerfilPorCodigo() {
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPerfilPorCodigo("TESTE"));
    }
}
