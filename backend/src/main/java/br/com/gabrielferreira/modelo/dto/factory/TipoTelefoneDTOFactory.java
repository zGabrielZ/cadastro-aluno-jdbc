package br.com.gabrielferreira.modelo.dto.factory;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.dto.TipoTelefoneViewDTO;

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
