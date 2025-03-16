package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.Genero;

import java.sql.SQLException;

public interface GeneroDAO {

    Genero buscarPorId(Long id) throws SQLException;

    Genero buscarGeneroPorCodigo(String codigo) throws SQLException;
}
