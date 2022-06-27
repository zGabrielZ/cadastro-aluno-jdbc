package br.com.gabrielferreira.modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Email implements Serializable {

    private static final long serialVersionUID = -8751392459776277462L;

    @EqualsAndHashCode.Include
    private Long id;
    private String endereco;

}
