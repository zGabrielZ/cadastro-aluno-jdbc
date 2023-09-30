package br.com.gabrielferreira.dto.create;

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
public class UsuarioCreateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private String nome;

    private String email;

    private String senha;

    private LocalDate dataNascimento;

    private String cpf;

    private String nomeSocial;

    private Long idGenero;

    private Long idPerfil;

    private List<TelefoneCreateDTO> telefones = new ArrayList<>();
}
