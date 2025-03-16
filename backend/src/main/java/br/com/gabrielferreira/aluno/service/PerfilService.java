package br.com.gabrielferreira.aluno.service;

import br.com.gabrielferreira.aluno.dto.PerfilDTO;
import br.com.gabrielferreira.aluno.model.Perfil;

public interface PerfilService {

    PerfilDTO buscarPorId(Long id);

    Perfil buscarPerfilPorCodigo(String codigo);

    Perfil buscarPerfilPorId(Long id);
}
