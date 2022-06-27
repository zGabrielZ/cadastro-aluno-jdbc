package br.com.gabrielferreira.conexao.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DadosBanco implements Serializable {

    private static final long serialVersionUID = 3338565127741337290L;

    private String url;
    private String usuario;
    private String senha;

}
