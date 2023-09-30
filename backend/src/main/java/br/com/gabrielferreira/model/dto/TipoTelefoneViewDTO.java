package br.com.gabrielferreira.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoTelefoneViewDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private Long id;

    private String descricao;

    private String codigo;
}
