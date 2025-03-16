package br.com.gabrielferreira.aluno.conexao.config;

import br.com.gabrielferreira.aluno.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.aluno.exception.BancoDeDadosException;

import java.io.InputStream;
import java.util.Properties;

import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

public class ConfigBandoDeDadosDevImpl implements ConfigBancoDeDados {

    private static final String AMBIENTE_DEV = "dev";

    @Override
    public InformacaoBancoDeDados getRecuperarDadosBanco() {
        String nomeArquivoBancoDados = "db-".concat(AMBIENTE_DEV).concat(".properties");
        try {
            ClassLoader classLoader = ConfigBandoDeDadosDevImpl.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(nomeArquivoBancoDados);

            if (inputStream == null) {
                throw new BancoDeDadosException("Ocorreu um erro ao recuperar os dados do banco de dados do ambiente de dev");
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            return InformacaoBancoDeDados.builder()
                    .url(properties.getProperty("url"))
                    .usuario(properties.getProperty("user"))
                    .senha(properties.getProperty("password"))
                    .build();
        } catch (Exception e){
            gerarLogWarn("Ocorreu um erro ao recuperar os dados do banco de dados do ambiente de dev, {}", e);
            throw new BancoDeDadosException("Ocorreu um erro ao recuperar os dados do banco de dados do ambiente de dev");
        }
    }
}
