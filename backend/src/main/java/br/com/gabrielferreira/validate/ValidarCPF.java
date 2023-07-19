package br.com.gabrielferreira.validate;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidarCPF {

    private ValidarCPF(){}

    public static boolean isCPFValido(String cpf){
        boolean isValido = true;
        try{
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e){
            isValido = false;
        }
        return isValido;
    }
}
