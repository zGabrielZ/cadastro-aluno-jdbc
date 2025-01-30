package br.com.gabrielferreira.aluno.dto.create;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record TelefoneCreateDTO(
        String ddd,
        String numero,
        Long idTipoTelefone
) implements Serializable {
}
