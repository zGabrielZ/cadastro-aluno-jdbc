package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.dto.GeneroViewDTO;
import org.junit.jupiter.api.*;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.TESTE;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GeneroServiceTest {

    private GeneroService generoService;

    @BeforeEach
    public void criarInstancias(){
        generoService = new GeneroService(DaoFactory.criarGeneroDao(TESTE));
    }

    @Test
    @DisplayName("Deve encontrar gênero por id")
    @Order(1)
    void deveEncontrarGeneroPorId(){
        GeneroViewDTO generoEncontrado = generoService.buscarPorId(1L);

        assertNotNull(generoEncontrado.getId());
        assertEquals("Masculino", generoEncontrado.getDescricao());
        assertEquals("MASCULINO", generoEncontrado.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar gênero por id")
    @Order(2)
    void naoDeveEncontrarGeneroPorId(){
        assertThrows(RegistroNaoEncontradoException.class, () -> generoService.buscarPorId(-1L));
    }

    @Test
    @DisplayName("Deve encontrar gênero quando informar o código")
    @Order(3)
    void deveEncontrarGeneroPorCodigo(){
        Genero genero = generoService.buscarGeneroPorCodigo("MASCULINO");

        assertNotNull(genero.getId());
        assertEquals("Masculino", genero.getDescricao());
        assertEquals("MASCULINO", genero.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar gênero quando informar código errado")
    @Order(4)
    void naoDeveEncontrarGeneroPorCodigo(){
        assertThrows(RegistroNaoEncontradoException.class, () -> generoService.buscarGeneroPorCodigo("TESTE"));
    }
}
