package br.com.gabrielferreira.aluno.dao.factory;

import br.com.gabrielferreira.aluno.conexao.ConexaoDB;
import br.com.gabrielferreira.aluno.dao.*;
import br.com.gabrielferreira.aluno.dao.impl.*;

public class DaoFactory {

    private DaoFactory() {}

    public static GeneroDAO criarGeneroDao() {
        return new GeneroDAOImpl(ConexaoDB.getInstance());
    }

    public static PerfilDAO criarPerfilDao() {
        return new PerfilDAOImpl(ConexaoDB.getInstance());
    }

    public static TelefoneDAO criarTelefoneDao() {
        return new TelefoneDAOImpl(ConexaoDB.getInstance());
    }

    public static TipoTelefoneDAO criarTipoTelefoneDao() {
        return new TipoTelefoneDAOImpl(ConexaoDB.getInstance());
    }

    public static UsuarioDAO criarUsuarioDao() {
        return new UsuarioDAOImpl(ConexaoDB.getInstance(), criarTelefoneDao());
    }
}
