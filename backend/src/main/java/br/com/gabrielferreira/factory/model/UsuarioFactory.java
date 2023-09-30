package br.com.gabrielferreira.factory.model;

import br.com.gabrielferreira.model.Genero;
import br.com.gabrielferreira.model.Usuario;
import br.com.gabrielferreira.dto.update.UsuarioUpdateDTO;
import br.com.gabrielferreira.dto.create.UsuarioCreateDTO;

import static br.com.gabrielferreira.factory.model.GeneroFactory.*;
import static br.com.gabrielferreira.factory.model.PerfilFactory.*;


public class UsuarioFactory {

    private UsuarioFactory(){}

    public static Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO){
        return Usuario.builder()
                .nome(usuarioCreateDTO.getNome())
                .email(usuarioCreateDTO.getEmail())
                .senha(usuarioCreateDTO.getSenha())
                .dataNascimento(usuarioCreateDTO.getDataNascimento())
                .cpf(usuarioCreateDTO.getCpf())
                .nomeSocial(usuarioCreateDTO.getNomeSocial())
                .genero(toGenero(usuarioCreateDTO.getIdGenero()))
                .perfil(toPerfil(usuarioCreateDTO.getIdPerfil()))
                .build();
    }

    public static void toUsuarioAtualizar(Usuario usuario, Genero generoEncontrado, UsuarioUpdateDTO usuarioUpdateDTO){
        if(usuario != null){
            usuario.setNome(usuarioUpdateDTO.getNome());
            usuario.setDataNascimento(usuarioUpdateDTO.getDataNascimento());
            usuario.setNomeSocial(usuarioUpdateDTO.getNomeSocial());
            usuario.setGenero(generoEncontrado);
        }
    }
}
