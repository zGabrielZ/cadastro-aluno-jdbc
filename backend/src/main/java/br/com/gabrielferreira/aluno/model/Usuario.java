package br.com.gabrielferreira.aluno.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"genero", "perfil", "telefones"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = -8629710608419903368L;

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String cpf;

    private LocalDate dataNascimento;

    private String nomeSocial;

    private Genero genero;

    private Perfil perfil;

    private List<Telefone> telefones = new ArrayList<>();

}
