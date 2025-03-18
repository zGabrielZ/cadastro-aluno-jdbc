package br.com.gabrielferreira.aluno.service.impl;

import br.com.gabrielferreira.aluno.ServiceIntegration;
import br.com.gabrielferreira.aluno.dao.TipoTelefoneDAO;
import br.com.gabrielferreira.aluno.dao.factory.DaoFactory;
import br.com.gabrielferreira.aluno.dto.TipoTelefoneDTO;
import br.com.gabrielferreira.aluno.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.aluno.model.TipoTelefone;
import br.com.gabrielferreira.aluno.service.TipoTelefoneService;
import br.com.gabrielferreira.aluno.service.factory.ServiceFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TipoTelefoneServiceImplImplTest extends ServiceIntegration {

    private TipoTelefoneService tipoTelefoneService;

    @BeforeEach
    public void criarInstancias() {
        TipoTelefoneDAO tipoTelefoneDAO = DaoFactory.criarTipoTelefoneDao();
        tipoTelefoneService = ServiceFactory.criarTipoTelefoneService(tipoTelefoneDAO);
    }

    @Test
    @DisplayName("Deve encontrar tipo de telefone quando informar o código")
    @Order(1)
    void deveEncontrarTipoTelefonePorCodigo() {
        TipoTelefone tipoTelefone = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");

        assertNotNull(tipoTelefone.getId());
        assertEquals("Residencial", tipoTelefone.getDescricao());
        assertEquals("RESIDENCIAL", tipoTelefone.getCodigo());
    }

    @Test
    @DisplayName("Não deve encontrar tipo de telefone quando informar código errado")
    @Order(2)
    void naoDeveEncontrarGenero() {
        assertThrows(RegistroNaoEncontradoException.class, () -> tipoTelefoneService.buscarTipoTelefonePorCodigo("TESTE"));
    }

    @Test
    @DisplayName("Deve encontrar tipo telefone por id")
    @Order(3)
    void deveEncontrarTipoTelefonePorId() {
        TipoTelefoneDTO tipoTelefoneViewDTO = tipoTelefoneService.buscarPorId(1L);

        assertNotNull(tipoTelefoneViewDTO.id());
        assertEquals("Residencial", tipoTelefoneViewDTO.descricao());
        assertEquals("RESIDENCIAL", tipoTelefoneViewDTO.codigo());
    }

    @Test
    @DisplayName("Não deve encontrar perfil por id")
    @Order(4)
    void naoDeveEncontrarPerfilPorId() {
        assertThrows(RegistroNaoEncontradoException.class, () -> tipoTelefoneService.buscarPorId(-1L));
    }
}
