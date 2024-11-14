package br.com.gabrielferreira.service;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.conexao.config.ConfigBandoDeDadosTestImpl;
import br.com.gabrielferreira.dao.GeneroDAO;
import br.com.gabrielferreira.dto.GeneroDTO;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.Genero;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GeneroServiceTest {

    private GeneroService generoService;

    @BeforeEach
    public void criarInstancias(){
        ConexaoBD conexaoBD = new ConexaoBD(new ConfigBandoDeDadosTestImpl());
        GeneroDAO generoDAO = new GeneroDAO(conexaoBD.getConnection());
        generoService = new GeneroService(generoDAO);
    }

    @Test
    @DisplayName("Deve encontrar gênero por id")
    @Order(1)
    void deveEncontrarGeneroPorId(){
        GeneroDTO generoEncontrado = generoService.buscarPorId(1L);

        assertNotNull(generoEncontrado.id());
        assertEquals("Masculino", generoEncontrado.descricao());
        assertEquals("MASCULINO", generoEncontrado.codigo());
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
