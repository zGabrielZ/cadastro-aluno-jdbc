package br.com.gabrielferreira.factory.dto;

import br.com.gabrielferreira.model.Genero;
import br.com.gabrielferreira.dto.view.GeneroViewDTO;

public class GeneroDTOFactory {

    private GeneroDTOFactory(){}

    public static GeneroViewDTO toGeneroViewDTO(Genero genero){
        if(genero != null){
            return GeneroViewDTO.builder()
                    .id(genero.getId())
                    .descricao(genero.getDescricao())
                    .codigo(genero.getCodigo())
                    .build();
        }
        return null;
    }
}
