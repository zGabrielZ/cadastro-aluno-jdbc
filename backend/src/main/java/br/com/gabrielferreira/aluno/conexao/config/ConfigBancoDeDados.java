package br.com.gabrielferreira.aluno.conexao.config;

import br.com.gabrielferreira.aluno.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.aluno.exception.BancoDeDadosException;
import br.com.gabrielferreira.aluno.exception.ErroException;

import java.io.InputStream;
import java.util.Properties;

import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

public class ConfigBancoDeDados {

    public InformacaoBancoDeDados getInformacaoBancoDeDados() {
        try {
            String ambiente = System.getProperty("AMBIENTE");
            return switch (ambiente) {
                case "DEV" -> getDev();
                case "TEST" -> getTest();
                default -> throw new ErroException("É necessário ter pelo menos um ambiente escolhido no banco de dados");
            };
        } catch (Exception e) {
            gerarLogWarn("Ocorreu um erro ao recuperar os dados do banco de dados, {}", e);
            throw new ErroException("É necessário ter pelo menos um ambiente escolhido no banco de dados");
        }
    }

    private InformacaoBancoDeDados getTest() {
        return getRecuperarDadosBanco("db-test.properties");
    }

    private InformacaoBancoDeDados getDev() {
        return getRecuperarDadosBanco("db-dev.properties");
    }

    public InformacaoBancoDeDados getRecuperarDadosBanco(String nomeArquivoBancoDados) {
        try {
            ClassLoader classLoader = ConfigBancoDeDados.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(nomeArquivoBancoDados);

            if (inputStream == null) {
                throw new BancoDeDadosException("Ocorreu um erro ao recuperar os dados do banco de dados");
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            return InformacaoBancoDeDados.builder()
                    .url(properties.getProperty("url"))
                    .usuario(properties.getProperty("user"))
                    .senha(properties.getProperty("password"))
                    .build();
        } catch (Exception e){
            gerarLogWarn("Ocorreu um erro ao recuperar os dados do banco de dados, {}", e);
            throw new BancoDeDadosException("Ocorreu um erro ao recuperar os dados do banco de dados");
        }
    }

}
