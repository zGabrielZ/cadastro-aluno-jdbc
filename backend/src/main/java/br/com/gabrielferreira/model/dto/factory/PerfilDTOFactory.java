package br.com.gabrielferreira.model.dto.factory;

import br.com.gabrielferreira.model.Perfil;
import br.com.gabrielferreira.model.dto.PerfilViewDTO;

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
