//package br.com.gabrielferreira.service;
//
//import br.com.gabrielferreira.dao.TipoTelefoneDAO;
//import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
//import br.com.gabrielferreira.modelo.TipoTelefone;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class TipoTelefoneServiceTest {
//
//    private TipoTelefoneDAO tipoTelefoneDAO;
//    private TipoTelefoneService tipoTelefoneService;
//
//    @BeforeEach
//    public void criarInstanciasTipoTelefone(){
//        tipoTelefoneDAO = mock(TipoTelefoneDAO.class);
//        tipoTelefoneService = new TipoTelefoneService(tipoTelefoneDAO);
//    }
//
//    @Test
//    @DisplayName("Deveria listar tipos telefones quando tiver dados no banco de dados.")
//    void deveListarTipoTelefones(){
//        // Cenário
//        List<TipoTelefone> tipoTelefones = new ArrayList<>();
//        tipoTelefones.add(TipoTelefone.builder().id(1L).descricao("Residencial").build());
//        tipoTelefones.add(TipoTelefone.builder().id(2L).descricao("Celular").build());
//        tipoTelefones.add(TipoTelefone.builder().id(3L).descricao("Fax").build());
//
//        // Mock para retornar lista de telefones
//        when(tipoTelefoneDAO.listaDeTipoTelefones()).thenReturn(tipoTelefones);
//
//        // Executando
//        List<TipoTelefone> tipoTelefonesResultado = tipoTelefoneService.listaDeTipoTelefones();
//
//        // Verificando
//        assertFalse(tipoTelefonesResultado.isEmpty());
//        assertEquals(3, tipoTelefonesResultado.size());
//    }
//
//    @Test
//    @DisplayName("Deveria encontrar tipo telefone por id quando tiver dados no banco de dados.")
//    void deveEncontrarTipoTelefonePorId(){
//        // Cenário
//        // Id pesquisado
//        Long idPesquisado = 1L;
//
//        TipoTelefone tipoTelefone = TipoTelefone.builder().id(1L).descricao("Residencial").build();
//
//        // Mock para retornar tipo telefone por id
//        when(tipoTelefoneDAO.buscarPorId(anyLong())).thenReturn(tipoTelefone);
//
//        // Executando
//        TipoTelefone tipoTelefoneResultado = tipoTelefoneService.buscarTipoTelefonePorId(idPesquisado);
//
//        // Verificando
//        assertEquals(tipoTelefone.getId(), tipoTelefoneResultado.getId());
//        assertEquals(tipoTelefone.getDescricao(), tipoTelefoneResultado.getDescricao());
//    }
//
//    @Test
//    @DisplayName("Não deveria encontrar tipo de telefone por id quando não tiver dados no banco de dados.")
//    void naoDeveEncontrarTipoTelefonePorId(){
//        // Cenário
//        // Id pesquisado
//        Long idPesquisado = 1L;
//
//        // Mock para retornar tipo telefone por id
//        when(tipoTelefoneDAO.buscarPorId(anyLong())).thenReturn(null);
//
//        // Executando e verificando
//        assertThrows(RegraDeNegocioException.class, () -> tipoTelefoneService.buscarTipoTelefonePorId(idPesquisado));
//    }
//
//}
