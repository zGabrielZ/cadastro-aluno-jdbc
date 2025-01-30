package br.com.gabrielferreira.aluno.conexao;

import br.com.gabrielferreira.aluno.conexao.config.ConfigBancoDeDados;
import br.com.gabrielferreira.aluno.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.aluno.exception.BancoDeDadosException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;

import static br.com.gabrielferreira.aluno.utils.LogUtils.*;

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
