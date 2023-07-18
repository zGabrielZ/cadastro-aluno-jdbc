package br.com.gabrielferreira.modelo.factory;

import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;

public class UsuarioFactory {

    private UsuarioFactory(){}

    public static Usuario toUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .dataNascimento(usuarioDTO.getDataNascimento())
                .cpf(usuarioDTO.getCpf())
                .build();
    }

    public static void toUsuarioAtualizar(Usuario usuario, UsuarioAtualizarDTO usuarioAtualizarDTO){
        if(usuario != null){
            usuario.setNome(usuarioAtualizarDTO.getNome());
            usuario.setDataNascimento(usuarioAtualizarDTO.getDataNascimento());
        }
    }
}
