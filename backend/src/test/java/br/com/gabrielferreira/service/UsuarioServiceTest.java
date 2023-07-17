package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        UsuarioDTO usuarioDTO = gerarUsuario("Gabriel", "gabrielemail@email.com", "123");

        UsuarioViewDTO usuarioViewDTO = usuarioService.inserir(usuarioDTO);

        assertNotNull(usuarioViewDTO.getId());

        usuarioService.deletarPorId(usuarioViewDTO.getId());
    }

    @Test
    @DisplayName("Deve encontrar usuário quando foi salvo")
    void deveEncontrarUsuarioSalvo(){
        UsuarioDTO usuarioDTO = gerarUsuario("José da Silva", "josesilva@email.com", "123");

        UsuarioViewDTO usuarioInsert = usuarioService.inserir(usuarioDTO);

        UsuarioViewDTO usuarioViewDTO = usuarioService.buscarPorId(usuarioInsert.getId());

        assertEquals(usuarioInsert.getId(), usuarioViewDTO.getId());
        assertEquals(usuarioInsert.getNome(), usuarioViewDTO.getNome());
        assertEquals(usuarioInsert.getEmail(), usuarioViewDTO.getEmail());

        usuarioService.deletarPorId(usuarioInsert.getId());
    }

    @Test
    @DisplayName("Deve atualizar o nome do usuário")
    void deveAtualizarNomeUsuario(){
        UsuarioDTO usuarioDTO = gerarUsuario("Marcos da Silva", "marcos@email.com", "123");

        UsuarioViewDTO usuarioInsert = usuarioService.inserir(usuarioDTO);

        UsuarioAtualizarDTO usuarioAtualizarDTO = gerarUsuarioAtualizar("Mariano da Silva Souza");

        UsuarioViewDTO usuarioAtualizado = usuarioService.atualizar(usuarioAtualizarDTO, usuarioInsert.getId());

        assertEquals(usuarioAtualizarDTO.getNome(), usuarioAtualizado.getNome());

        usuarioService.deletarPorId(usuarioInsert.getId());
    }

    public UsuarioDTO gerarUsuario(String nome, String email, String senha){
        return UsuarioDTO.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();
    }

    public UsuarioAtualizarDTO gerarUsuarioAtualizar(String nome){
        return UsuarioAtualizarDTO.builder()
                .nome(nome)
                .build();
    }
}
