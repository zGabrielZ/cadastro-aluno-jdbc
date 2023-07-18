package br.com.gabrielferreira.modelo.factory;

import br.com.gabrielferreira.modelo.Genero;

public class GeneroFactory {

    private GeneroFactory(){}

    public static Genero toGenero(Long id){
        if(id != null){
            return Genero.builder()
                    .id(id)
                    .build();
        }
        return null;
    }
}
