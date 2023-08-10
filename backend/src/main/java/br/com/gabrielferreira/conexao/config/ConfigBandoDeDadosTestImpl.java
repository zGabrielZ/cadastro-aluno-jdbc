package br.com.gabrielferreira.conexao.config;

import br.com.gabrielferreira.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.exceptions.BancoDeDadosException;

import java.io.FileInputStream;
import java.util.Properties;

import static br.com.gabrielferreira.utils.LogUtils.gerarLogWarn;

public class ConfigBandoDeDadosTestImpl implements ConfigBancoDeDados {

    private static final String AMBIENTE_TESTE = "test";

    @Override
    public InformacaoBancoDeDados getRecuperarDadosBanco() {
        String nomeArquivoBancoDados = "db-".concat(AMBIENTE_TESTE).concat(".properties");
        try(FileInputStream fileInputStream = new FileInputStream(nomeArquivoBancoDados)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return InformacaoBancoDeDados.builder()
                    .url(properties.getProperty("url"))
                    .usuario(properties.getProperty("user"))
                    .senha(properties.getProperty("password"))
                    .build();
        } catch (Exception e){
            gerarLogWarn("Ocorreu um erro ao recuperar os dados do banco de dados do ambiente de teste, {}", e);
            throw new BancoDeDadosException("Ocorreu um erro ao recuperar os dados do banco de dados do ambiente de teste");
        }
    }
}
