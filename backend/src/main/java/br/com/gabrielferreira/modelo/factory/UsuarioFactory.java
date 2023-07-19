package br.com.gabrielferreira.modelo.factory;

import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;

import static br.com.gabrielferreira.modelo.factory.GeneroFactory.*;
import static br.com.gabrielferreira.modelo.factory.PerfilFactory.*;


public class UsuarioFactory {

    private UsuarioFactory(){}

    public static Usuario toUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .dataNascimento(usuarioDTO.getDataNascimento())
                .cpf(usuarioDTO.getCpf())
                .nomeSocial(usuarioDTO.getNomeSocial())
                .genero(toGenero(usuarioDTO.getIdGenero()))
                .perfil(toPerfil(usuarioDTO.getIdPerfil()))
                .build();
    }

    public static void toUsuarioAtualizar(Usuario usuario, Genero generoEncontrado, UsuarioAtualizarDTO usuarioAtualizarDTO){
        if(usuario != null){
            usuario.setNome(usuarioAtualizarDTO.getNome());
            usuario.setDataNascimento(usuarioAtualizarDTO.getDataNascimento());
            usuario.setNomeSocial(usuarioAtualizarDTO.getNomeSocial());
            usuario.setGenero(generoEncontrado);
        }
    }
}
