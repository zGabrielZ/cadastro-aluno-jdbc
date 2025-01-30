package br.com.gabrielferreira.aluno.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record TelefoneDTO(
        Long id,
        String ddd,
        String numero,
        TipoTelefoneDTO tipoTelefone
) implements Serializable {
}
