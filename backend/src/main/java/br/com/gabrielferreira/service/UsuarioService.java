package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;

import static br.com.gabrielferreira.modelo.factory.UsuarioFactory.*;
import static br.com.gabrielferreira.modelo.dto.factory.UsuarioDTOFactory.*;
import static br.com.gabrielferreira.validate.ValidarUsuarioService.*;
import static br.com.gabrielferreira.utils.StringCriptografar.*;

@Slf4j
@AllArgsConstructor
public class UsuarioService implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private UsuarioDAO usuarioDAO;

    private GeneroService generoService;

    public UsuarioViewDTO inserir(UsuarioDTO usuarioDTO){
        Usuario usuario = toUsuario(usuarioDTO);
        validarCamposNaoInformadosCadastro(usuario);
        usuario.setSenha(criptarCampo(usuario.getSenha()));

        try {
            usuarioDAO.inserir(usuario);
        } catch (Exception e){
            log.warn("Erro ao inserir o usuário, {}", e.getMessage());
            if(e instanceof SQLException sqlException){
                if(sqlException.getMessage().contains("ck_email_unique")){
                    throw new RegraDeNegocioException("Este e-mail informado já foi cadastrado");
                } else if(sqlException.getMessage().contains("ck_cpf_unique")){
                    throw new RegraDeNegocioException("Este CPF informado já foi cadastrado");
                } else if(sqlException.getMessage().contains("usuario_id_genero_fkey")){
                    throw new RegraDeNegocioException("Gênero informado não encontrado");
                } else if(sqlException.getMessage().contains("usuario_id_perfil_fkey")){
                    throw new RegraDeNegocioException("Perfil informado não encontrado");
                }
            }
            throw new ErroException("Erro ao salvar o usuário, tente mais tarde.");
        }

        return toUsuarioViewDTO(usuario);
    }

    public UsuarioViewDTO buscarPorId(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        return toUsuarioViewDTO(usuario);
    }

    public UsuarioViewDTO atualizar(UsuarioAtualizarDTO usuarioAtualizarDTO, Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        Genero generoEncontrado = generoService.buscarGeneroPorId(usuarioAtualizarDTO.getIdGenero());

        try {
            toUsuarioAtualizar(usuario, generoEncontrado, usuarioAtualizarDTO);
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
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if(usuario == null){
                throw new ErroException("Usuário não encontrado");
            }
            return usuario;
        } catch (Exception e){
            log.warn("Erro ao buscar o usuário, {}", e.getMessage());
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }
}
