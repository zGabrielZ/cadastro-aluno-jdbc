package br.com.gabrielferreira.factory.dto;

import br.com.gabrielferreira.model.Telefone;
import br.com.gabrielferreira.dto.TelefoneViewDTO;

import java.util.List;

import static br.com.gabrielferreira.factory.dto.TipoTelefoneDTOFactory.*;

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
