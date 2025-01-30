package br.com.gabrielferreira.aluno.model.factory;
import br.com.gabrielferreira.aluno.model.Perfil;

public class PerfilFactory {

    private PerfilFactory(){}

    public static Perfil toPerfil(Long id){
        if(id != null){
            return Perfil.builder()
                    .id(id)
                    .build();
        }
        return null;
    }
}
