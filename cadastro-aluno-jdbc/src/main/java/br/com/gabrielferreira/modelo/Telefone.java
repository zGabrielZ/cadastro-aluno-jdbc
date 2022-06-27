package br.com.gabrielferreira.modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Telefone implements Serializable {

    private static final long serialVersionUID = -729755399030361273L;

    private Long id;
    private String ddd;

    @EqualsAndHashCode.Include
    private String numero;

    private TipoTelefone tipoTelefone;
    private Aluno aluno;
}
