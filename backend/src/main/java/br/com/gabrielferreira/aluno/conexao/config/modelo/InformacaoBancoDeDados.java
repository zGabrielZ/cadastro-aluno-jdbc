package br.com.gabrielferreira.aluno.conexao.config.modelo;

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
public class InformacaoBancoDeDados implements Serializable {

    @Serial
    private static final long serialVersionUID = -8629710608419903368L;

    private String url;

    private String usuario;

    private String senha;
}
