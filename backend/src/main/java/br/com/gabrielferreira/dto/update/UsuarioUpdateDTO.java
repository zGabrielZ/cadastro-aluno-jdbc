package br.com.gabrielferreira.dto.update;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record UsuarioUpdateDTO(
        String nome,
        LocalDate dataNascimento,
        String nomeSocial,
        Long idGenero
) implements Serializable {
}
