package br.com.gabrielferreira.conexao.config;

import br.com.gabrielferreira.exceptions.BancoDeDadosException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.util.Properties;

@Slf4j
public class ConfigBancoDados {

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String usuario;

    @Getter
    @Setter
    private String senha;

    public ConfigBancoDados(String ambiente){
        getRecuperarDadosBanco(ambiente);
    }

    public void getRecuperarDadosBanco(String ambiente){
        String nomeArquivoBancoDados = "db-".concat(ambiente).concat(".properties");
        try(FileInputStream fileInputStream = new FileInputStream(nomeArquivoBancoDados)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            this.setUrl(properties.getProperty("url"));
            this.setUsuario(properties.getProperty("user"));
            this.setSenha(properties.getProperty("password"));

        } catch (Exception e){
            log.warn("Ocorreu um erro ao recuperar os dados do banco de dados, {}", e.getMessage());
            throw new BancoDeDadosException("Ocorreu um erro ao recuperar os dados do banco de dados");
        }
    }
}
