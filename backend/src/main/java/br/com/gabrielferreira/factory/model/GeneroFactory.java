package br.com.gabrielferreira.factory.model;

import br.com.gabrielferreira.model.Genero;

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
