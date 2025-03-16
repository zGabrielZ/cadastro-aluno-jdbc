package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.TipoTelefone;

import java.sql.SQLException;

public interface TipoTelefoneDAO {

    TipoTelefone buscarPorId(Long id) throws SQLException;

    TipoTelefone buscarTipoTelefonePorCodigo(String codigo) throws SQLException;
}
