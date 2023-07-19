package br.com.gabrielferreira.modelo.factory;
import br.com.gabrielferreira.modelo.Perfil;

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
