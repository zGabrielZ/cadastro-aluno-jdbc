package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.Telefone;

import java.sql.SQLException;
import java.util.List;

public interface TelefoneDAO {

    void inserirTelefone(Telefone telefone) throws SQLException;

    List<Telefone> buscarTelefonesPorIdUsuario(Long idUsuario) throws SQLException;

    void deleteTudo() throws SQLException;
}
