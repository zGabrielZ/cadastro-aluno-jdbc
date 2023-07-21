package br.com.gabrielferreira.utils;

import org.mindrot.jbcrypt.BCrypt;

public class StringCriptografar {

    private StringCriptografar(){}

    public static String criptarCampo(String campo) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(campo, salt);
    }
}
