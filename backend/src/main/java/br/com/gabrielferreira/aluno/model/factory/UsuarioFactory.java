package br.com.gabrielferreira.aluno.model.factory;

import br.com.gabrielferreira.aluno.dto.create.UsuarioCreateDTO;
import br.com.gabrielferreira.aluno.dto.update.UsuarioUpdateDTO;
import br.com.gabrielferreira.aluno.model.Genero;
import br.com.gabrielferreira.aluno.model.Usuario;

import static br.com.gabrielferreira.aluno.model.factory.GeneroFactory.toGenero;
import static br.com.gabrielferreira.aluno.model.factory.PerfilFactory.toPerfil;

public class UsuarioFactory {

    private UsuarioFactory(){}

    public static Usuario toUsuario(UsuarioCreateDTO usuarioCreateDTO){
        return Usuario.builder()
                .nome(usuarioCreateDTO.nome())
                .email(usuarioCreateDTO.email())
                .senha(usuarioCreateDTO.senha())
                .dataNascimento(usuarioCreateDTO.dataNascimento())
                .cpf(usuarioCreateDTO.cpf())
                .nomeSocial(usuarioCreateDTO.nomeSocial())
                .genero(toGenero(usuarioCreateDTO.idGenero()))
                .perfil(toPerfil(usuarioCreateDTO.idPerfil()))
                .build();
    }

    public static void toUsuarioAtualizar(Usuario usuario, Genero generoEncontrado, UsuarioUpdateDTO usuarioUpdateDTO){
        if(usuario != null){
            usuario.setNome(usuarioUpdateDTO.nome());
            usuario.setDataNascimento(usuarioUpdateDTO.dataNascimento());
            usuario.setNomeSocial(usuarioUpdateDTO.nomeSocial());
            usuario.setGenero(generoEncontrado);
        }
    }
}
