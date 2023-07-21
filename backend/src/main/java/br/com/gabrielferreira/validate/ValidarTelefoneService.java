package br.com.gabrielferreira.validate;

import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.Usuario;

import static br.com.gabrielferreira.utils.ConstantesUtils.*;
import static br.com.gabrielferreira.utils.StringUtils.*;

public class ValidarTelefoneService {

    private ValidarTelefoneService(){}

    public static void validarCamposNaoInformadosCadastro(Telefone telefone){
        validarDdd(telefone);
        validarNumero(telefone);
        validarTipoTelefone(telefone.getTipoTelefone());
    }

    public static void validarUsuario(Usuario usuario){
        if(usuario == null || usuario.getId() == null){
            throw new RegraDeNegocioException("É necessário informar o usuário");
        }
    }

    private static void validarDdd(Telefone telefone){
        validarCampoVazio(telefone.getDdd(), "É necessário informar o ddd do telefone do usuário");
        telefone.setDdd(telefone.getDdd().trim());

        validarTamanho(telefone.getDdd(), 2, 2, "É necessário informar o ddd do telefone do usuário até 2 caracteres");
    }

    private static void validarNumero(Telefone telefone){
        validarCampoVazio(telefone.getNumero(), "É necessário informar o número do telefone do usuário");
        telefone.setNumero(telefone.getNumero().trim());

        validarTamanho(telefone.getNumero(), 8, 9, "É necessário informar o número do telefone do usuário até 255 caracteres");
        validarTelefoneValido(telefone.getNumero());
    }

    private static void validarTipoTelefone(TipoTelefone tipoTelefone){
        if(tipoTelefone == null || tipoTelefone.getId() == null){
            throw new RegraDeNegocioException("É necessário informar o tipo de telefone");
        }
    }

    private static void validarTelefoneValido(String numeroTelefone){
        boolean isNaoPossuiDigito = isNaoPossuiDigito(numeroTelefone);
        if(isNaoPossuiDigito){
            throw new RegraDeNegocioException("Digite um número com apenas dígitos do telefone para o usuário");
        }
    }
}
