package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.dao.UsuarioDAO;
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

    private final UsuarioDAO usuarioDAO = DaoFactory.criarUsuarioDao(TESTE);

    @BeforeEach
    public void criarInstancias(){
        usuarioService = new UsuarioService(usuarioDAO);
    }

    @Test
    @DisplayName("Deve salvar usuário quando informar os valores corretamente")
    void deveSalvarUsuario(){
        UsuarioDTO usuarioDTO = gerarUsuario("Gabriel", "gabrielemail@email.com", "123", LocalDate.of(1997, 12, 26), "32890461092");

        UsuarioViewDTO usuarioViewDTO = usuarioService.inserir(usuarioDTO);

        assertNotNull(usuarioViewDTO.getId());
        assertNotNull(usuarioViewDTO.getDataNascimento());
        assertNotNull(usuarioViewDTO.getCpf());

        usuarioService.deletarPorId(usuarioViewDTO.getId());
    }

    @Test
    @DisplayName("Deve encontrar usuário quando foi salvo")
    void deveEncontrarUsuarioSalvo(){
        UsuarioDTO usuarioDTO = gerarUsuario("José da Silva", "josesilva@email.com", "123", LocalDate.of(1985, 11, 20), "68721457069");

        UsuarioViewDTO usuarioInsert = usuarioService.inserir(usuarioDTO);

        UsuarioViewDTO usuarioViewDTO = usuarioService.buscarPorId(usuarioInsert.getId());

        assertEquals(usuarioInsert.getId(), usuarioViewDTO.getId());
        assertEquals(usuarioInsert.getNome(), usuarioViewDTO.getNome());
        assertEquals(usuarioInsert.getEmail(), usuarioViewDTO.getEmail());
        assertEquals(usuarioInsert.getDataNascimento(), usuarioViewDTO.getDataNascimento());
        assertEquals(usuarioInsert.getCpf(), usuarioViewDTO.getCpf());

        usuarioService.deletarPorId(usuarioInsert.getId());
    }

    @Test
    @DisplayName("Deve atualizar o usuário")
    void deveAtualizarUsuario(){
        UsuarioDTO usuarioDTO = gerarUsuario("Marcos da Silva", "marcos@email.com", "123", LocalDate.of(2000, 12, 20), "04707182003");

        UsuarioViewDTO usuarioInsert = usuarioService.inserir(usuarioDTO);

        UsuarioAtualizarDTO usuarioAtualizarDTO = gerarUsuarioAtualizar("Mariano da Silva Souza", LocalDate.of(1995, 10, 5));

        UsuarioViewDTO usuarioAtualizado = usuarioService.atualizar(usuarioAtualizarDTO, usuarioInsert.getId());

        assertEquals(usuarioAtualizarDTO.getNome(), usuarioAtualizado.getNome());
        assertEquals(usuarioAtualizarDTO.getDataNascimento(), usuarioAtualizado.getDataNascimento());

        usuarioService.deletarPorId(usuarioInsert.getId());
    }

    public UsuarioDTO gerarUsuario(String nome, String email, String senha, LocalDate dataNascimento, String cpf){
        return UsuarioDTO.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .build();
    }

    public UsuarioAtualizarDTO gerarUsuarioAtualizar(String nome, LocalDate dataNascimento){
        return UsuarioAtualizarDTO.builder()
                .nome(nome)
                .dataNascimento(dataNascimento)
                .build();
    }
}
