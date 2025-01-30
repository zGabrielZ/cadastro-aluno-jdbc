package br.com.gabrielferreira.aluno.utils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {

    private LogUtils(){}

    public static void gerarLogWarn(String mensagem, Exception exception){
        log.warn(mensagem, exception.getMessage());
    }

    public static void gerarLogInfo(String mensagem){
        log.info(mensagem);
    }
}
