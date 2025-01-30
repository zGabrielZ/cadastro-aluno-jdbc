package br.com.gabrielferreira.aluno.utils;

import br.com.gabrielferreira.aluno.exception.RegraDeNegocioException;
import br.com.gabrielferreira.aluno.model.Perfil;
import br.com.gabrielferreira.aluno.model.Usuario;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static br.com.gabrielferreira.aluno.utils.DataUtils.*;
import static br.com.gabrielferreira.aluno.utils.ValidarCPFUtils.*;
import static br.com.gabrielferreira.aluno.utils.StringUtils.*;

import static br.com.gabrielferreira.aluno.utils.ConstantesUtils.*;

public class ValidarUsuarioUtils {

    private ValidarUsuarioUtils(){}

    public static void validarCamposNaoInformadosCadastroUsuario(Usuario usuario){
        validarNome(usuario);
        validarEmail(usuario);
        validarSenha(usuario);
        validarDataNascimento(usuario.getDataNascimento());
        validarCpf(usuario);
        validarNomeSocial(usuario);
        validarPerfil(usuario.getPerfil());
    }

    public static void validarCamposNaoInformadosAtualizarUsuario(Usuario usuario){
        validarNome(usuario);
        validarDataNascimento(usuario.getDataNascimento());
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

        validarTamanho(usuario.getCpf(), 1, 11, "É necessário informar o cpf do usuário até 11 caracteres");
        validarCpfValido(usuario.getCpf());
    }

    private static void validarCpfValido(String cpf){
        boolean isNaoPossuiDigito = isNaoPossuiDigito(cpf);
        if(isNaoPossuiDigito){
            throw new RegraDeNegocioException("Digite o CPF com apenas dígitos do usuário");
        }

        if(!isCPFValido(cpf)){
            throw new RegraDeNegocioException("CPF do usuário informado é inválido");
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
        if(usuario.getNomeSocial() != null && !usuario.getNomeSocial().isBlank()){
            usuario.setNomeSocial(usuario.getNomeSocial().trim());
            validarTamanho(usuario.getNomeSocial(), 1, 255, "É necessário informar o nome social do usuário até 255 caracteres");
        }
    }

    private static void validarPerfil(Perfil perfil){
        if(perfil == null || perfil.getId() == null){
            throw new RegraDeNegocioException("É necessário informar o perfil do usuário");
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
}
