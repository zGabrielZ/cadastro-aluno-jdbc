package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.modelo.dto.GeneroViewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.TESTE;
import static org.junit.jupiter.api.Assertions.*;

class GeneroServiceTest {

    private GeneroService generoService;

    @BeforeEach
    public void criarInstancias(){
        generoService = new GeneroService(DaoFactory.criarGeneroDao(TESTE));
    }

    @Test
    @DisplayName("Deve encontrar gênero quando foi salvo")
    void deveEncontrarGeneroSalvo(){
        GeneroViewDTO generoEncontrado = generoService.buscarPorId(1L);

        assertNotNull(generoEncontrado.getId());
        assertEquals("Masculino", generoEncontrado.getDescricao());
    }

    @Test
    @DisplayName("Não deve encontrar gênero quando não tiver cadastrado")
    void naoDeveEncontrarGenero(){
        assertThrows(ErroException.class, () -> generoService.buscarPorId(-1L));
    }
}
