package br.com.gabrielferreira.validate;

import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Usuario;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static br.com.gabrielferreira.utils.DataUtils.*;
import static br.com.gabrielferreira.utils.MascaraUtils.*;
import static br.com.gabrielferreira.validate.ValidarCPF.*;
import static br.com.gabrielferreira.utils.StringUtils.*;

public class ValidarUsuarioService {

    private ValidarUsuarioService(){}

    public static void validarCamposNaoInformadosCadastro(Usuario usuario){
        validarNome(usuario);
        validarEmail(usuario);
        validarSenha(usuario);
        validarDataNascimento(usuario.getDataNascimento());
        validarCpf(usuario);
        validarNomeSocial(usuario);
    }

    private static void validarNome(Usuario usuario){
        validarCampoVazio(usuario.getNome(), "É necessário informar o nome do usuário");
        usuario.setNome(usuario.getNome().trim());

        validarTamanho(usuario.getNome(), 1, 255, "É necessário informar o nome do usuário até 255 caracteres");
    }

    private static void validarEmail(Usuario usuario){
        validarCampoVazio(usuario.getEmail(), "É necessário informar o e-mail do usuário");
        usuario.setEmail(usuario.getEmail().trim());

        validarTamanho(usuario.getEmail(), 1, 255, "É necessário informar o e-mail do usuário até 255 caracteres");
        validarEmailValido(usuario.getEmail());
    }

    private static void validarSenha(Usuario usuario){
        validarCampoVazio(usuario.getSenha(), "É necessário informar a senha do usuário");
        usuario.setSenha(usuario.getSenha().trim());

        validarTamanho(usuario.getSenha(), 1, 20, "É necessário informar a senha do usuário até 20 caracteres");
        validarSenhaFracaOuForte(usuario.getSenha());
    }

    private static void validarDataNascimento(LocalDate dataNascimento){
        if(dataNascimento == null){
            throw new RegraDeNegocioException("É necessário informar a data de nascimento do usuário");
        }

        if(dataNascimento.isAfter(dataAtualBrasil())){
            throw new RegraDeNegocioException("A data do nascimento informado é maior que a data atual");
        }
    }

    private static void validarCpf(Usuario usuario){
        validarCampoVazio(usuario.getCpf(), "É necessário informar o CPF do usuário");
        usuario.setCpf(usuario.getCpf().trim());
        usuario.setCpf(limparMascaraCpf(usuario.getCpf()));

        validarTamanho(usuario.getCpf(), 1, 11, "É necessário informar o cpf do usuário até 11 caracteres");

        if(!isCPFValido(usuario.getCpf())){
            throw new RegraDeNegocioException("CPF do usuãrio informado é inválido");
        }
    }

    private static void validarEmailValido(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(email).matches()){
            throw new RegraDeNegocioException("Digite um endereço do e-mail válido para o usuário");
        }
    }

    private static void validarNomeSocial(Usuario usuario){
        if(usuario.getNomeSocial() != null || !usuario.getNomeSocial().isBlank()){
            usuario.setNomeSocial(usuario.getNomeSocial().trim());
            validarTamanho(usuario.getNomeSocial(), 1, 255, "É necessário informar o nome social do usuário até 255 caracteres");
        }
    }

    private static void validarSenhaFracaOuForte(String senha){
        StringBuilder stringBuilder = new StringBuilder();

        if(!isPossuiLetraMaiuscula(senha)){
            stringBuilder.append("A senha informada tem que ter pelo menos uma letra maiúscula\n");
        }

        if(!isPossuiLetraMinuscula(senha)){
            stringBuilder.append("A senha informada tem que ter pelo menos uma letra minúscula\n");
        }

        if(!isPossuiNumero(senha)){
            stringBuilder.append("A senha informada tem que ter pelo menos um número\n");
        }

        if(!isPossuiCaracteresEspeciais(senha)){
            stringBuilder.append("A senha informada tem que ter pelo menos um caractere especial");
        }

        if(!stringBuilder.isEmpty()){
            throw new RegraDeNegocioException(stringBuilder.toString());
        }

    }

    private static void validarCampoVazio(String campo, String msg){
        if(campo == null || campo.isBlank()){
            throw new RegraDeNegocioException(msg);
        }
    }

    private static void validarTamanho(String campo, int inicio, int fim, String msg){
        if(!(campo.length() >= inicio && campo.length() <= fim)){
            throw new RegraDeNegocioException(msg);
        }
    }
}
