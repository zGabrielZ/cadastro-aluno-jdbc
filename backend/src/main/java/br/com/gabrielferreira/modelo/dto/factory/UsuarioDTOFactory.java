package br.com.gabrielferreira.modelo.dto.factory;

import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;

import static br.com.gabrielferreira.modelo.dto.factory.GeneroDTOFactory.*;

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
                .build();
    }
}
