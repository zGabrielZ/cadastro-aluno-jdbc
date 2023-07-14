package br.com.gabrielferreira.modelo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TipoTelefone implements Serializable {

    private static final long serialVersionUID = 6718148424160444545L;

    @EqualsAndHashCode.Include
    private Long id;

    private String descricao;

}
