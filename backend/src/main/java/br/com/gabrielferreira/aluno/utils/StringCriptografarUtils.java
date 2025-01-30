package br.com.gabrielferreira.aluno.utils;

import org.mindrot.jbcrypt.BCrypt;

public class StringCriptografarUtils {

    private StringCriptografarUtils(){}

    public static String criptarCampo(String campo) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(campo, salt);
    }
}
