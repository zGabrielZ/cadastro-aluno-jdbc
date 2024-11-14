package br.com.gabrielferreira.model.factory;

import br.com.gabrielferreira.dto.create.TelefoneCreateDTO;
import br.com.gabrielferreira.model.Telefone;

import java.util.List;

import static br.com.gabrielferreira.model.factory.TipoTelefoneFactory.toTipoTelefone;

public class TelefoneFactory {

    private TelefoneFactory(){}

    public static List<Telefone> toTelefones(List<TelefoneCreateDTO> telefoneCreateDTOS){
        return telefoneCreateDTOS.stream().map(TelefoneFactory::toTelefone).toList();
    }

    public static Telefone toTelefone(TelefoneCreateDTO telefoneCreateDTO){
        return Telefone.builder()
                .ddd(telefoneCreateDTO.ddd())
                .numero(telefoneCreateDTO.numero())
                .tipoTelefone(toTipoTelefone(telefoneCreateDTO.idTipoTelefone()))
                .build();
    }
}
