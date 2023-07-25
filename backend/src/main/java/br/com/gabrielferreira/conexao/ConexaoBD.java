package br.com.gabrielferreira.conexao;

import br.com.gabrielferreira.conexao.config.ConfigBancoDados;
import br.com.gabrielferreira.exceptions.BancoDeDadosException;
import java.sql.Connection;
import java.sql.DriverManager;

import static br.com.gabrielferreira.utils.LogUtils.*;

public class ConexaoBD {

    private static Connection connection = null;

    public ConexaoBD(String ambiente) {
        conectarBancoDeDados(ambiente);
    }

    private static void conectarBancoDeDados(String ambiente){
        try {
            ConfigBancoDados configBancoDados = new ConfigBancoDados(ambiente);
            if(connection == null){
                connection = DriverManager.getConnection(configBancoDados.getUrl(), configBancoDados.getUsuario(), configBancoDados.getSenha());
                connection.setAutoCommit(false);
            }
        } catch (Exception e){
            gerarLogWarn("Ocorreu um erro ao conectar no banco de dados, {}", e);
            throw new BancoDeDadosException("Ocorreu um erro ao conectar no banco de dados");
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
