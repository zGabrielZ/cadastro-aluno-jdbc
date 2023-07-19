package br.com.gabrielferreira.modelo.dto;

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
public class TelefoneDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private String ddd;

    private String numero;

    private Long idTipoTelefone;
}
