package br.com.gabrielferreira.dto.factory;

import br.com.gabrielferreira.dto.PerfilDTO;
import br.com.gabrielferreira.model.Perfil;

public class PerfilDTOFactory {

    private PerfilDTOFactory(){}

    public static PerfilDTO toPerfilDTO(Perfil perfil){
        if(perfil != null){
            return PerfilDTO.builder()
                    .id(perfil.getId())
                    .descricao(perfil.getDescricao())
                    .codigo(perfil.getCodigo())
                    .build();
        }
        return null;
    }
}
