package br.com.gabrielferreira.aluno.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PerfilDTO(
        Long id,
        String descricao,
        String codigo
) implements Serializable {
}
