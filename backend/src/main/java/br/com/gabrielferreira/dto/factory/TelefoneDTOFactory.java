package br.com.gabrielferreira.dto.factory;

import br.com.gabrielferreira.dto.TelefoneDTO;
import br.com.gabrielferreira.model.Telefone;

import java.util.List;

import static br.com.gabrielferreira.dto.factory.TipoTelefoneDTOFactory.toTipoTelefoneDTO;

public class TelefoneDTOFactory {

    private TelefoneDTOFactory(){}

    public static List<TelefoneDTO> toTelefonesDTO(List<Telefone> telefones){
        return telefones.stream().map(TelefoneDTOFactory::toTelefoneDTO).toList();
    }

    public static TelefoneDTO toTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .tipoTelefone(toTipoTelefoneDTO(telefone.getTipoTelefone()))
                .build();
    }
}
