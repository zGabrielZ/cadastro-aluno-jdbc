package br.com.gabrielferreira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAtualizarDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private String nome;

    private LocalDate dataNascimento;

    private String nomeSocial;

    private Long idGenero;
}
