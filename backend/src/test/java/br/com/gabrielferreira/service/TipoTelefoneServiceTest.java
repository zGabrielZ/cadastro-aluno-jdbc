package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.dto.TipoTelefoneViewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.TESTE;
import static org.junit.jupiter.api.Assertions.*;

class TipoTelefoneServiceTest {

    private TipoTelefoneService tipoTelefoneService;

    @BeforeEach
    public void criarInstancias(){
        tipoTelefoneService = new TipoTelefoneService(DaoFactory.criarTipoTelefoneDao(TESTE));
    }

    @Test
    @DisplayName("Deve encontrar tipo de telefone quando informar o código")
    void deveEncontrarTipoTelefonePorCodigo(){
        TipoTelefone tipoTelefone = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");

        assertNotNull(tipoTelefone.getId());
        assertEquals("Residencial", tipoTelefone.getDescricao());
        assertEquals("RESIDENCIAL", tipoTelefone.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar tipo de telefone quando informar código errado")
    void naoDeveEncontrarGenero(){
        assertThrows(RegistroNaoEncontradoException.class, () -> tipoTelefoneService.buscarTipoTelefonePorCodigo("TESTE"));
    }

    @Test
    @DisplayName("Deve encontrar tipo telefone por id")
    void deveEncontrarTipoTelefonePorId(){
        TipoTelefoneViewDTO tipoTelefoneViewDTO = tipoTelefoneService.buscarPorId(1L);

        assertNotNull(tipoTelefoneViewDTO.getId());
        assertEquals("Residencial", tipoTelefoneViewDTO.getDescricao());
        assertEquals("RESIDENCIAL", tipoTelefoneViewDTO.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil por id")
    void naoDeveEncontrarPerfilPorId(){
        assertThrows(RegistroNaoEncontradoException.class, () -> tipoTelefoneService.buscarPorId(-1L));
    }
}
