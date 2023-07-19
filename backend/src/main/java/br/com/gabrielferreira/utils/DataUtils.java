package br.com.gabrielferreira.utils;

import java.time.LocalDate;
import java.time.ZoneId;

public class DataUtils {

    private DataUtils(){}

    public static LocalDate dataAtualBrasil(){
        return LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }
}
