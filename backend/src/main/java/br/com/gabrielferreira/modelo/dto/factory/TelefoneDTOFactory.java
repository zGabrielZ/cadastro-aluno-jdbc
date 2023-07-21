package br.com.gabrielferreira.modelo.dto.factory;

import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.dto.TelefoneViewDTO;

import java.util.List;

import static br.com.gabrielferreira.modelo.dto.factory.TipoTelefoneDTOFactory.*;

public class TelefoneDTOFactory {

    private TelefoneDTOFactory(){}

    public static List<TelefoneViewDTO> toTelefonesViewDTO(List<Telefone> telefones){
        return telefones.stream().map(TelefoneDTOFactory::toTelefoneViewDTO).toList();
    }

    public static TelefoneViewDTO toTelefoneViewDTO(Telefone telefone){
        return TelefoneViewDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .tipoTelefone(toTipoTelefoneViewDTO(telefone.getTipoTelefone()))
                .build();
    }
}
