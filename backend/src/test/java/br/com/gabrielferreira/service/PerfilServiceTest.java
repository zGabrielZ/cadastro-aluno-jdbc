package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
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
    @DisplayName("Deve encontrar perfil quando foi salvo")
    void deveEncontrarPerfilSalvo(){
        PerfilViewDTO perfilEncontrado = perfilService.buscarPorId(1L);

        assertNotNull(perfilEncontrado.getId());
        assertEquals("Administrador", perfilEncontrado.getDescricao());
    }

    @Test
    @DisplayName("Não deve encontrar perfil quando não tiver cadastrado")
    void naoDeveEncontrarPerfil(){
        assertThrows(RegistroNaoEncontradoException.class, () -> perfilService.buscarPorId(-1L));
    }
}
