package br.com.gabrielferreira.aluno.dto.factory;
import br.com.gabrielferreira.aluno.dto.TipoTelefoneDTO;
import br.com.gabrielferreira.aluno.model.TipoTelefone;

public class TipoTelefoneDTOFactory {

    private TipoTelefoneDTOFactory(){}

    public static TipoTelefoneDTO toTipoTelefoneDTO(TipoTelefone tipoTelefone){
        if(tipoTelefone != null){
            return TipoTelefoneDTO.builder()
                    .id(tipoTelefone.getId())
                    .descricao(tipoTelefone.getDescricao())
                    .codigo(tipoTelefone.getCodigo())
                    .build();
        }
        return null;
    }
}
