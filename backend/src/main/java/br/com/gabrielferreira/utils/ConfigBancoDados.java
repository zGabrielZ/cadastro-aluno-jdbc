package br.com.gabrielferreira.utils;

import br.com.gabrielferreira.conexao.modelo.DadosBanco;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

@Slf4j
public class ConfigBancoDados implements Serializable {

    private static final long serialVersionUID = 8580433007347756622L;

    public DadosBanco getRecuperarDadosBanco(String caminhoConfigBancoDados){
        DadosBanco dadosBanco = new DadosBanco();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoConfigBancoDados)))){
            while (reader.ready()){
                String linha = reader.readLine();
                if(linha != null && !linha.isEmpty()){
                    String[] delimitador = linha.split(";");
                    dadosBanco.setUrl(delimitador[0]);
                    dadosBanco.setUsuario(delimitador[1]);
                    dadosBanco.setSenha(delimitador[2]);
                }
            }
        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
        }

        return dadosBanco;
    }
}
