package br.com.gabrielferreira.factory.model;
import br.com.gabrielferreira.model.Telefone;
import br.com.gabrielferreira.dto.create.TelefoneCreateDTO;

import java.util.List;

import static br.com.gabrielferreira.factory.model.TipoTelefoneFactory.*;

public class TelefoneFactory {

    private TelefoneFactory(){}

    public static List<Telefone> toTelefones(List<TelefoneCreateDTO> telefoneCreateDTOS){
        return telefoneCreateDTOS.stream().map(TelefoneFactory::toTelefone).toList();
    }

    public static Telefone toTelefone(TelefoneCreateDTO telefoneCreateDTO){
        return Telefone.builder()
                .ddd(telefoneCreateDTO.getDdd())
                .numero(telefoneCreateDTO.getNumero())
                .tipoTelefone(toTipoTelefone(telefoneCreateDTO.getIdTipoTelefone()))
                .build();
    }
}
