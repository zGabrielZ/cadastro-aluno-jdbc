package br.com.gabrielferreira.aluno.dto.factory;

import br.com.gabrielferreira.aluno.dto.GeneroDTO;
import br.com.gabrielferreira.aluno.model.Genero;

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
