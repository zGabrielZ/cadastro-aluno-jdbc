package br.com.gabrielferreira.modelo.factory;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.dto.TelefoneDTO;

import java.util.List;

import static br.com.gabrielferreira.modelo.factory.TipoTelefoneFactory.*;

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
