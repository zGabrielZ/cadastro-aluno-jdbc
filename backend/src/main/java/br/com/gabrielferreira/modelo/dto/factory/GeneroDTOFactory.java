package br.com.gabrielferreira.modelo.dto.factory;

import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.dto.GeneroViewDTO;

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
