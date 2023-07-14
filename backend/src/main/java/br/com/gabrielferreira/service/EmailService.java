package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.EmailDAO;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class EmailService implements Serializable {

    private static final long serialVersionUID = 6730307453958143191L;

    private EmailDAO emailDAO;

    public Email inserir(Email email){
        try {
            verificarEmail(email);

            String endereco = email.getEndereco().trim();
            validarEmailValido(endereco);
            verificarEmailCadastrado(endereco);
            emailDAO.inserir(email);
        } catch (Exception e){
            log.warn("Erro : {}",e.getMessage());
        }
        return email;
    }

    private void verificarEmail(Email email){
        if(email.getEndereco() == null && email.getEndereco().isBlank()){
            throw new RegraDeNegocioException("Endereço do e-mail é obrigatório.");
        }
    }

    private void validarEmailValido(String endereco){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(endereco).matches()){
            throw new RegraDeNegocioException("Digite um endereço do e-mail válido.");
        }
    }

    private void verificarEmailCadastrado(String endereco){
        if(emailDAO.checarEmailCadastrado(endereco)){
            throw new RegraDeNegocioException("Este e-mail já foi cadastrado.");
        }
    }
}
