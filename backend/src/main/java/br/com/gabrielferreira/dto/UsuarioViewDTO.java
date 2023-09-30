package br.com.gabrielferreira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private PerfilViewDTO perfil;

    private List<TelefoneViewDTO> telefones = new ArrayList<>();
}
