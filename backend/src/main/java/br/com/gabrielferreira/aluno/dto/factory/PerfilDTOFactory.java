package br.com.gabrielferreira.aluno.dto.factory;

import br.com.gabrielferreira.aluno.dto.PerfilDTO;
import br.com.gabrielferreira.aluno.model.Perfil;

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
