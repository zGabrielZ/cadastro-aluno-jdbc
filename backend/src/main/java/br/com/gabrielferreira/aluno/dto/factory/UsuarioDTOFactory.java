package br.com.gabrielferreira.aluno.dto.factory;

import br.com.gabrielferreira.aluno.dto.TelefoneDTO;
import br.com.gabrielferreira.aluno.dto.UsuarioDTO;
import br.com.gabrielferreira.aluno.model.Usuario;

import java.util.List;

import static br.com.gabrielferreira.aluno.dto.factory.GeneroDTOFactory.toGeneroDTO;
import static br.com.gabrielferreira.aluno.dto.factory.PerfilDTOFactory.toPerfilDTO;

public class UsuarioDTOFactory {

    private UsuarioDTOFactory(){}

    public static UsuarioDTO toUsuarioDTO(Usuario usuario, List<TelefoneDTO> telefoneViewDTOList){
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .nomeSocial(usuario.getNomeSocial())
                .genero(toGeneroDTO(usuario.getGenero()))
                .perfil(toPerfilDTO(usuario.getPerfil()))
                .telefones(telefoneViewDTOList)
                .build();
    }
}
