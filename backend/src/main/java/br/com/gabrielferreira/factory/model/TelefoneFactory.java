package br.com.gabrielferreira.factory.model;
import br.com.gabrielferreira.model.Telefone;
import br.com.gabrielferreira.dto.TelefoneDTO;

import java.util.List;

import static br.com.gabrielferreira.factory.model.TipoTelefoneFactory.*;

public class TelefoneFactory {

    private TelefoneFactory(){}

    public static List<Telefone> toTelefones(List<TelefoneDTO> telefoneDTOS){
        return telefoneDTOS.stream().map(TelefoneFactory::toTelefone).toList();
    }

    public static Telefone toTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .tipoTelefone(toTipoTelefone(telefoneDTO.getIdTipoTelefone()))
                .build();
    }
}
