package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.Serial;
import java.io.Serializable;

import static br.com.gabrielferreira.modelo.factory.UsuarioFactory.*;
import static br.com.gabrielferreira.modelo.dto.factory.UsuarioDTOFactory.*;

@Slf4j
@AllArgsConstructor
public class UsuarioService implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private UsuarioDAO usuarioDAO;

    public UsuarioViewDTO inserir(UsuarioDTO usuarioDTO){
        Usuario usuario = toUsuario(usuarioDTO);

        try {
            usuarioDAO.inserir(usuario);
        } catch (Exception e){
            log.warn("Erro ao inserir o usuário, {}", e.getMessage());
            throw new ErroException("Erro ao salvar o usuário, tente mais tarde.");
        }

        return toUsuarioViewDTO(usuario);
    }

    public UsuarioViewDTO buscarPorId(Long id){
        Usuario usuario;
        try {
           usuario = buscarUsuarioPorId(id);
        } catch (Exception e){
            log.warn("Erro ao buscar o usuário, {}", e.getMessage());
            throw new ErroException("Erro ao buscar o usuário, tente mais tarde.");
        }

        return toUsuarioViewDTO(usuario);
    }

    public UsuarioViewDTO atualizar(UsuarioAtualizarDTO usuarioAtualizarDTO, Long id){
        Usuario usuario = buscarUsuarioPorId(id);

        try {
            toUsuarioAtualizar(usuario, usuarioAtualizarDTO);
            usuarioDAO.atualizar(usuario, usuario.getId());
        } catch (Exception e){
            log.warn("Erro ao atualizar o usuário, {}", e.getMessage());
            throw new ErroException("Erro ao atualizar o usuário, tente mais tarde.");
        }

        return toUsuarioViewDTO(usuario);
    }

    public void deletarPorId(Long id){
        Usuario usuario;
        try {
            usuario = buscarUsuarioPorId(id);
            usuarioDAO.deletarPorId(usuario.getId());
        } catch (Exception e){
            log.warn("Erro ao deletar o usuário, {}", e.getMessage());
            throw new ErroException("Erro ao deletar o usuário, tente mais tarde.");
        }
    }

    private Usuario buscarUsuarioPorId(Long id){
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if(usuario == null){
            throw new RegistroNaoEncontradoException("Usuário não encontrado");
        }
        return usuario;
    }
}
