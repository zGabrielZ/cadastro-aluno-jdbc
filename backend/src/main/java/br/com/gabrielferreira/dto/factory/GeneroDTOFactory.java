package br.com.gabrielferreira.dto.factory;

import br.com.gabrielferreira.dto.GeneroDTO;
import br.com.gabrielferreira.model.Genero;

public class GeneroDTOFactory {

    private GeneroDTOFactory(){}

    public static GeneroDTO toGeneroDTO(Genero genero){
        if(genero != null){
            return GeneroDTO.builder()
                    .id(genero.getId())
                    .descricao(genero.getDescricao())
                    .codigo(genero.getCodigo())
                    .build();
        }
        return null;
    }
}
