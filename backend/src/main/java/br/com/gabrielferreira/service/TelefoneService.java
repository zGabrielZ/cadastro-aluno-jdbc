package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.TelefoneDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import static br.com.gabrielferreira.modelo.factory.TelefoneFactory.*;
import static br.com.gabrielferreira.modelo.dto.factory.TelefoneDTOFactory.*;
import static br.com.gabrielferreira.validate.ValidarTelefoneService.*;

@Slf4j
@AllArgsConstructor
public class TelefoneService implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private static final Long COMPRIMENTO_NUMERO_RESIDENCIAL = 8L;
    private static final Long COMPRIMENTO_NUMERO_CELULAR = 9L;

    private TelefoneDAO telefoneDAO;

    private TipoTelefoneService tipoTelefoneService;


    // FIXME: VALIDAR NUMERO REPETIDO
    public List<TelefoneViewDTO> inserir(Usuario usuario, List<TelefoneDTO> telefonesDtos){
        validarUsuario(usuario);
        List<Telefone> telefones = toTelefones(telefonesDtos);

        telefones.forEach(telefone -> {
            validarCamposNaoInformadosCadastro(telefone);
            validarTipoTelefoneComNumero(telefone.getNumero(), telefone.getTipoTelefone());
            telefone.setUsuario(usuario);
        });

        try {
            for (Telefone telefone : telefones) {
                telefoneDAO.inserir(telefone);
            }
        } catch (Exception e){
            log.warn("Erro ao inserir o telefone, {}", e.getMessage());
            if(e instanceof SQLException sqlException){
                if(sqlException.getMessage().contains("telefone_id_usuario_fkey")){
                    throw new RegraDeNegocioException("Usuário informado não encontrado");
                } else if(sqlException.getMessage().contains("telefone_id_tipo_telefone_fkey")){
                    throw new RegraDeNegocioException("Tipo de telefone informado não encontrado");
                }
            }
            throw new ErroException("Erro ao salvar o telefone, tente mais tarde.");
        }

        return toTelefonesViewDTO(telefones);
    }

    private void validarTipoTelefoneComNumero(String numero, TipoTelefone tipoTelefone){
        TipoTelefone tipoTelefoneCelular = tipoTelefoneService.buscarTipoTelefonePorCodigo("CELULAR");

        if(numero.length() == COMPRIMENTO_NUMERO_RESIDENCIAL && tipoTelefone.getId().equals(tipoTelefoneCelular.getId())){
            throw new RegraDeNegocioException("Você está inserindo um telefone para o tipo de telefone como celular");
        }

        TipoTelefone tipoTelefoneResidencial = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");
        if(numero.length() == COMPRIMENTO_NUMERO_CELULAR && tipoTelefone.getId().equals(tipoTelefoneResidencial.getId())){
            throw new RegraDeNegocioException("Você está inserindo um celular para o tipo de telefone como residencial");
        }
    }
}
