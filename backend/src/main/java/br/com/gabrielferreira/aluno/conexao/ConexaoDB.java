package br.com.gabrielferreira.aluno.conexao;

import br.com.gabrielferreira.aluno.conexao.config.ConfigBancoDeDados;
import br.com.gabrielferreira.aluno.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.aluno.exception.BancoDeDadosException;

import java.sql.Connection;
import java.sql.DriverManager;

import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogInfo;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

public class ConexaoDB {

    private static Connection connection = null;

    private ConexaoDB() {}

    public static Connection getInstance() {
        if (connection == null) {
            try {
                InformacaoBancoDeDados informacaoBancoDeDados = new ConfigBancoDeDados().getInformacaoBancoDeDados();
                connection = DriverManager.getConnection(informacaoBancoDeDados.getUrl(), informacaoBancoDeDados.getUsuario(), informacaoBancoDeDados.getSenha());
                connection.setAutoCommit(false);
                gerarLogInfo("Banco conectado !!!");
            } catch (Exception e){
                gerarLogWarn("Ocorreu um erro ao conectar no banco de dados, {}", e);
                throw new BancoDeDadosException("Ocorreu um erro ao conectar no banco de dados");
            }
        }
        return connection;
    }
}
