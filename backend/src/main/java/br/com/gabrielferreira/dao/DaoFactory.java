package br.com.gabrielferreira.dao;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum;

public class DaoFactory {

    private DaoFactory(){}

    public static UsuarioDAO criarUsuarioDao(BancoDeDadosAmbienteEnum bancoDeDadosAmbienteEnum){
        return new UsuarioDAO(new ConexaoBD(bancoDeDadosAmbienteEnum.getDescricao()).getConnection());
    }

    public static GeneroDAO criarGeneroDao(BancoDeDadosAmbienteEnum bancoDeDadosAmbienteEnum){
        return new GeneroDAO(new ConexaoBD(bancoDeDadosAmbienteEnum.getDescricao()).getConnection());
    }

    public static PerfilDAO criarPerfilDao(BancoDeDadosAmbienteEnum bancoDeDadosAmbienteEnum){
        return new PerfilDAO(new ConexaoBD(bancoDeDadosAmbienteEnum.getDescricao()).getConnection());
    }
}
