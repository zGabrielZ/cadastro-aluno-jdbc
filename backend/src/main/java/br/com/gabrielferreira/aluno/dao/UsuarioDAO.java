package br.com.gabrielferreira.aluno.dao;

import br.com.gabrielferreira.aluno.model.Telefone;
import br.com.gabrielferreira.aluno.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {

    void inserir(Usuario usuario) throws SQLException;

    void inserirUsuarioComTelefones(Usuario usuario, List<Telefone> telefones) throws SQLException;

    Usuario buscarPorId(Long id) throws SQLException;

    void atualizar(Usuario usuario, Long id) throws SQLException;

    void deletarPorId(Long id) throws SQLException;

    void deletarTelefonesPorIdUsuario(Long id) throws SQLException;

    void deleteTudo() throws SQLException;
}
