package br.com.gabrielferreira.factory.dto;

import br.com.gabrielferreira.model.Usuario;
import br.com.gabrielferreira.dto.view.UsuarioViewDTO;

import static br.com.gabrielferreira.factory.dto.GeneroDTOFactory.*;
import static br.com.gabrielferreira.factory.dto.PerfilDTOFactory.*;

public class UsuarioDTOFactory {

    private UsuarioDTOFactory(){}

    public static UsuarioViewDTO toUsuarioViewDTO(Usuario usuario){
        return UsuarioViewDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .nomeSocial(usuario.getNomeSocial())
                .genero(toGeneroViewDTO(usuario.getGenero()))
                .perfil(toPerfilViewDTO(usuario.getPerfil()))
                .build();
    }
}
