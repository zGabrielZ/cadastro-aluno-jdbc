package br.com.gabrielferreira.aluno.model.factory;
import br.com.gabrielferreira.aluno.model.TipoTelefone;

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
