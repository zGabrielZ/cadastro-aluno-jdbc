package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @BeforeEach
    public void criarInstancias(){
        UsuarioDAO usuarioDAO = DaoFactory.criarUsuarioDao(TESTE);
        GeneroService generoService = new GeneroService(DaoFactory.criarGeneroDao(TESTE));

        usuarioService = new UsuarioService(usuarioDAO, generoService);
    }

    @Test
    @DisplayName("Deve salvar usuário quando informar os valores corretamente")
    void deveSalvarUsuario(){
        UsuarioDTO usuarioAoSalvar = gerarUsuario("Gabriel", "gabrielemail@email.com", "123",
                LocalDate.of(1997, 12, 26), "32890461092", "Gabriel Social", 1L);

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioAoSalvar);

        assertNotNull(usuarioResultado.getId());
        assertEquals(usuarioAoSalvar.getNome(), usuarioResultado.getNome());
        assertEquals(usuarioAoSalvar.getEmail(), usuarioResultado.getEmail());
        assertNotNull(usuarioAoSalvar.getSenha());
        assertEquals(usuarioAoSalvar.getDataNascimento(), usuarioResultado.getDataNascimento());
        assertEquals(usuarioAoSalvar.getCpf(), usuarioResultado.getCpf());
        assertEquals(usuarioAoSalvar.getNomeSocial(), usuarioResultado.getNomeSocial());
        assertEquals(usuarioAoSalvar.getIdGenero(), usuarioResultado.getGenero().getId());

        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Deve encontrar usuário quando foi salvo")
    void deveEncontrarUsuarioSalvo(){
        UsuarioDTO usuarioAoSalvar = gerarUsuario("José da Silva", "josesilva@email.com", "123",
                LocalDate.of(1985, 11, 20), "68721457069", "Jose Social", 1L);

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioAoSalvar);

        UsuarioViewDTO usuarioResultadoBusca = usuarioService.buscarPorId(usuarioResultado.getId());

        assertEquals(usuarioResultado.getId(), usuarioResultadoBusca.getId());
        assertEquals(usuarioResultado.getNome(), usuarioResultadoBusca.getNome());
        assertEquals(usuarioResultado.getEmail(), usuarioResultadoBusca.getEmail());
        assertEquals(usuarioResultado.getDataNascimento(), usuarioResultadoBusca.getDataNascimento());
        assertEquals(usuarioResultado.getCpf(), usuarioResultadoBusca.getCpf());
        assertEquals(usuarioResultado.getNomeSocial(), usuarioResultadoBusca.getNomeSocial());
        assertEquals(usuarioResultado.getGenero().getId(), usuarioResultadoBusca.getGenero().getId());

        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Deve atualizar o usuário")
    void deveAtualizarUsuario(){
        UsuarioDTO usuarioAoSalvar = gerarUsuario("Marcos da Silva", "marcos@email.com", "123",
                LocalDate.of(2000, 12, 20), "04707182003", "Marcos Social", 1L);

        UsuarioViewDTO usuarioResultadoInserir = usuarioService.inserir(usuarioAoSalvar);

        UsuarioAtualizarDTO usuarioAoAtualizar = gerarUsuarioAtualizar("Mariano da Silva Souza", LocalDate.of(1995, 10, 5)
                , "Marcos Social 2", 2L);

        UsuarioViewDTO usuarioResultadoAtualizar = usuarioService.atualizar(usuarioAoAtualizar, usuarioResultadoInserir.getId());

        assertEquals(usuarioAoAtualizar.getNome(), usuarioResultadoAtualizar.getNome());
        assertEquals(usuarioAoAtualizar.getDataNascimento(), usuarioResultadoAtualizar.getDataNascimento());
        assertEquals(usuarioAoAtualizar.getNomeSocial(), usuarioResultadoAtualizar.getNomeSocial());
        assertEquals(usuarioAoAtualizar.getIdGenero(), usuarioResultadoAtualizar.getGenero().getId());

        usuarioService.deletarPorId(usuarioResultadoInserir.getId());
    }

    @Test
    @DisplayName("Não deve encontrar usuário quando não tiver cadastrado")
    void naoDeveEncontrarUsuario(){
        assertThrows(ErroException.class, () -> usuarioService.buscarPorId(-1L));
    }

    public UsuarioDTO gerarUsuario(String nome, String email, String senha, LocalDate dataNascimento, String cpf, String nomeSocial, Long idGenero){
        return UsuarioDTO.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .nomeSocial(nomeSocial)
                .idGenero(idGenero)
                .build();
    }

    public UsuarioAtualizarDTO gerarUsuarioAtualizar(String nome, LocalDate dataNascimento, String nomeSocial, Long idGenero){
        return UsuarioAtualizarDTO.builder()
                .nome(nome)
                .dataNascimento(dataNascimento)
                .nomeSocial(nomeSocial)
                .idGenero(idGenero)
                .build();
    }
}
