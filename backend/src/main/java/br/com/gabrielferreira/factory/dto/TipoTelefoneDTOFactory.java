package br.com.gabrielferreira.factory.dto;
import br.com.gabrielferreira.model.TipoTelefone;
import br.com.gabrielferreira.dto.TipoTelefoneViewDTO;

public class TipoTelefoneDTOFactory {

    private TipoTelefoneDTOFactory(){}

    public static TipoTelefoneViewDTO toTipoTelefoneViewDTO(TipoTelefone tipoTelefone){
        if(tipoTelefone != null){
            return TipoTelefoneViewDTO.builder()
                    .id(tipoTelefone.getId())
                    .descricao(tipoTelefone.getDescricao())
                    .codigo(tipoTelefone.getCodigo())
                    .build();
        }
        return null;
    }
}
