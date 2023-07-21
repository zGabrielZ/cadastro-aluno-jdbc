package br.com.gabrielferreira.modelo.factory;
import br.com.gabrielferreira.modelo.TipoTelefone;

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
