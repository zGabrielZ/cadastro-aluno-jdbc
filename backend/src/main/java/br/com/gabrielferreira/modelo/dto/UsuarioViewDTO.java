package br.com.gabrielferreira.modelo.dto;

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
public class UsuarioViewDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private Long id;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private String cpf;

    private String nomeSocial;

    private GeneroViewDTO genero;
}
