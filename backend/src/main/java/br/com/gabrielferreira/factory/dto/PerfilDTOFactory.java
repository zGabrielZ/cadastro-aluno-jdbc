package br.com.gabrielferreira.factory.dto;

import br.com.gabrielferreira.model.Perfil;
import br.com.gabrielferreira.dto.view.PerfilViewDTO;

public class PerfilDTOFactory {

    private PerfilDTOFactory(){}

    public static PerfilViewDTO toPerfilViewDTO(Perfil perfil){
        if(perfil != null){
            return PerfilViewDTO.builder()
                    .id(perfil.getId())
                    .descricao(perfil.getDescricao())
                    .codigo(perfil.getCodigo())
                    .build();
        }
        return null;
    }
}
