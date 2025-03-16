package br.com.gabrielferreira.aluno.service;

import br.com.gabrielferreira.aluno.dto.GeneroDTO;
import br.com.gabrielferreira.aluno.model.Genero;

public interface GeneroService {

    GeneroDTO buscarPorId(Long id);

    Genero buscarGeneroPorCodigo(String codigo);

    Genero buscarGeneroPorId(Long id);
}
