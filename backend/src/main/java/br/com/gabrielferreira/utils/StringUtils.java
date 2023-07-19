package br.com.gabrielferreira.utils;

import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils(){}

    private static final String REGEX_CARACTERES_ESPECIAIS = "[!@#$%&*?]";

    public static boolean isPossuiLetraMaiuscula(String campo){
        char[] letras = campo.toCharArray();
        boolean isLetraMaiuscula = false;
        for (char letra : letras) {
            if(Character.isUpperCase(letra)){
                isLetraMaiuscula = true;
            }
        }
        return isLetraMaiuscula;
    }

    public static boolean isPossuiLetraMinuscula(String campo){
        char[] letras = campo.toCharArray();
        boolean isLetraMinuscula = false;
        for (char letra : letras) {
            if(Character.isLowerCase(letra)){
                isLetraMinuscula = true;
            }
        }
        return isLetraMinuscula;
    }

    public static boolean isPossuiNumero(String campo){
        char[] letras = campo.toCharArray();
        boolean isLetraNumero = false;
        for (char letra : letras) {
            if(Character.isDigit(letra)){
                isLetraNumero = true;
            }
        }
        return isLetraNumero;
    }

    public static boolean isPossuiCaracteresEspeciais(String campo){
        char[] letras = campo.toCharArray();
        boolean isLetraCaracterEspecial = false;
        for (char letra : letras) {
            if(Pattern.matches(REGEX_CARACTERES_ESPECIAIS, String.valueOf(letra))){
                isLetraCaracterEspecial = true;
            }
        }
        return isLetraCaracterEspecial;
    }
}
