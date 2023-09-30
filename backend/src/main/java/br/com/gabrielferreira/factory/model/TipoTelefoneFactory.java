package br.com.gabrielferreira.factory.model;
import br.com.gabrielferreira.model.TipoTelefone;

public class TipoTelefoneFactory {

    private TipoTelefoneFactory(){}

    public static TipoTelefone toTipoTelefone(Long id){
        if(id != null){
            return TipoTelefone.builder()
                    .id(id)
                    .build();
        }
        return null;
    }
}
