package br.com.gabrielferreira.conexao;

import br.com.gabrielferreira.conexao.modelo.DadosBanco;
import br.com.gabrielferreira.utils.ConfigBancoDados;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class ConexaoBD implements Serializable {

    private static final long serialVersionUID = 3233797159978930910L;

    private static Connection connection = null;

    // Já conecta, nao vai ser preciso instanciar
    static {
        conectarBancoDeDados();
    }

    // Ao chamar o construtor, vai conectar
    public ConexaoBD() {
        conectarBancoDeDados();
    }

    private static void conectarBancoDeDados(){
        try {
            if(connection == null){
                DadosBanco dadosBanco = new ConfigBancoDados().getRecuperarDadosBanco("ConfigBancoDadosDev");
                connection = DriverManager.getConnection(dadosBanco.getUrl(),dadosBanco.getUsuario(), dadosBanco.getSenha());
            }
        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
