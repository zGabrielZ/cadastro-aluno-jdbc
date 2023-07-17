package br.com.gabrielferreira.service.v2;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.dao.v2.AlunoDAO;

import java.io.Serializable;
import java.sql.Connection;

public class AlunoService implements Serializable {

    private static final long serialVersionUID = 6730307453958143191L;

    private Connection connection;
    private AlunoDAO alunoDAO;

    public AlunoService(String caminhoBanco){
        this.connection = new ConexaoBD(caminhoBanco).getConnection();
        this.alunoDAO = new AlunoDAO(connection);
    }
}
