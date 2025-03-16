package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.Perfil;

import java.sql.SQLException;

public interface PerfilDAO {

    Perfil buscarPorId(Long id) throws SQLException;

    Perfil buscarPerfilPorCodigo(String codigo) throws SQLException;
}
