package br.com.gabrielferreira.conexao;

import br.com.gabrielferreira.conexao.config.ConfigBancoDados;
import br.com.gabrielferreira.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class ConexaoBD implements Serializable {

    @Serial
    private static final long serialVersionUID = 3233797159978930910L;

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
            log.warn("Ocorreu um erro ao conectar no banco de dados, {}",e.getMessage());
            throw new BancoDeDadosException("Ocorreu um erro ao conectar no banco de dados");
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
