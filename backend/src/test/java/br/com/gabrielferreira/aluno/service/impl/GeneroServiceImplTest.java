package br.com.gabrielferreira.aluno.service.impl;

import br.com.gabrielferreira.aluno.ServiceIntegration;
import br.com.gabrielferreira.aluno.dao.GeneroDAO;
import br.com.gabrielferreira.aluno.dao.factory.DaoFactory;
import br.com.gabrielferreira.aluno.dto.GeneroDTO;
import br.com.gabrielferreira.aluno.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.aluno.model.Genero;
import br.com.gabrielferreira.aluno.service.GeneroService;
import br.com.gabrielferreira.aluno.service.factory.ServiceFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GeneroServiceImplTest extends ServiceIntegration {

    private GeneroService generoService;

    @BeforeEach
    public void criarInstancias() {
        GeneroDAO generoDAO = DaoFactory.criarGeneroDao();
        generoService = ServiceFactory.criarGeneroService(generoDAO);
    }

    @Test
    @DisplayName("Deve encontrar gênero por id")
    @Order(1)
    void deveEncontrarGeneroPorId() {
        GeneroDTO generoEncontrado = generoService.buscarPorId(1L);

        assertNotNull(generoEncontrado.id());
        assertEquals("Masculino", generoEncontrado.descricao());
        assertEquals("MASCULINO", generoEncontrado.codigo());
    }

    @Test
    @DisplayName("Não deve encontrar gênero por id")
    @Order(2)
    void naoDeveEncontrarGeneroPorId() {
        assertThrows(RegistroNaoEncontradoException.class, () -> generoService.buscarPorId(-1L));
    }

    @Test
    @DisplayName("Deve encontrar gênero quando informar o código")
    @Order(3)
    void deveEncontrarGeneroPorCodigo() {
        Genero genero = generoService.buscarGeneroPorCodigo("MASCULINO");

        assertNotNull(genero.getId());
        assertEquals("Masculino", genero.getDescricao());
        assertEquals("MASCULINO", genero.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar gênero quando informar código errado")
    @Order(4)
    void naoDeveEncontrarGeneroPorCodigo() {
        assertThrows(RegistroNaoEncontradoException.class, () -> generoService.buscarGeneroPorCodigo("TESTE"));
    }
}
