package br.com.gabrielferreira.modelo;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Aluno implements Serializable {

    private static final long serialVersionUID = -8629710608419903368L;

    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String cpf;
    private Email email;
    private Set<Telefone> telefones = new HashSet<>();
}
