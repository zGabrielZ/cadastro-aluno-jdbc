package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.Perfil;
import br.com.gabrielferreira.modelo.dto.PerfilViewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.TESTE;
import static org.junit.jupiter.api.Assertions.*;

class PerfilServiceTest {

    private PerfilService perfilService;

    @BeforeEach
    public void criarInstancias(){
        perfilService = new PerfilService(DaoFactory.criarPerfilDao(TESTE));
    }

    @Test
    @DisplayName("Deve encontrar perfil por id")
    void deveEncontrarPerfilPorId(){
        PerfilViewDTO perfilEncontrado = perfilService.buscarPorId(1L);

        assertNotNull(perfilEncontrado.getId());
        assertEquals("Administrador", perfilEncontrado.getDescricao());
        assertEquals("ADMINISTRADOR", perfilEncontrado.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil por id")
    void naoDeveEncontrarPerfilPorId(){
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPorId(-1L));
    }

    @Test
    @DisplayName("Deve encontrar perfil quando informar o código")
    void deveEncontrarGeneroPorCodigo(){
        Perfil perfil = perfilService.buscarPerfilPorCodigo("ADMINISTRADOR");

        assertNotNull(perfil.getId());
        assertEquals("Administrador", perfil.getDescricao());
        assertEquals("ADMINISTRADOR", perfil.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil quando informar código errado")
    void naoDeveEncontrarPerfilPorCodigo(){
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPerfilPorCodigo("TESTE"));
    }
}
