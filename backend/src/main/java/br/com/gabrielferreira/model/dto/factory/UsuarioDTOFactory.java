package br.com.gabrielferreira.model.dto.factory;

import br.com.gabrielferreira.model.Usuario;
import br.com.gabrielferreira.model.dto.UsuarioViewDTO;

import static br.com.gabrielferreira.model.dto.factory.GeneroDTOFactory.*;
import static br.com.gabrielferreira.model.dto.factory.PerfilDTOFactory.*;

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
