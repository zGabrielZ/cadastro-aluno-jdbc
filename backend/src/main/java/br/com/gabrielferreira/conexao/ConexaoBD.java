package br.com.gabrielferreira.conexao;

import br.com.gabrielferreira.conexao.config.ConfigBancoDeDados;
import br.com.gabrielferreira.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.exception.BancoDeDadosException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;

import static br.com.gabrielferreira.utils.LogUtils.*;

public class ConexaoBD {

    @Getter
    private Connection connection = null;

    private final ConfigBancoDeDados configBancoDeDados;

    public ConexaoBD(ConfigBancoDeDados configBancoDeDados){
        this.configBancoDeDados = configBancoDeDados;
        conectarBancoDeDados();
    }

    private void conectarBancoDeDados(){
        try {
            InformacaoBancoDeDados informacaoBancoDeDados = configBancoDeDados.getRecuperarDadosBanco();
            if(connection == null){
                connection = DriverManager.getConnection(informacaoBancoDeDados.getUrl(), informacaoBancoDeDados.getUsuario(), informacaoBancoDeDados.getSenha());
                connection.setAutoCommit(false);
            }
        } catch (Exception e){
            gerarLogWarn("Ocorreu um erro ao conectar no banco de dados, {}", e);
            throw new BancoDeDadosException("Ocorreu um erro ao conectar no banco de dados");
        }
    }
}
