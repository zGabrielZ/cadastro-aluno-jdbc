package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.TelefoneDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.dto.TelefoneViewDTO;
import lombok.AllArgsConstructor;
import java.util.List;

import static br.com.gabrielferreira.modelo.dto.factory.TelefoneDTOFactory.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

@AllArgsConstructor
public class TelefoneService {

    private static final Long COMPRIMENTO_NUMERO_RESIDENCIAL = 8L;
    private static final Long COMPRIMENTO_NUMERO_CELULAR = 9L;

    private TelefoneDAO telefoneDAO;

    private TipoTelefoneService tipoTelefoneService;

    public List<TelefoneViewDTO> buscarTelefonesPorIdUsuario(Long idUsuario){
        List<Telefone> telefones = buscarTelefones(idUsuario);
        return toTelefonesViewDTO(telefones);
    }

    public void validarTipoTelefoneComNumero(String numero, TipoTelefone tipoTelefone){
        TipoTelefone tipoTelefoneCelular = tipoTelefoneService.buscarTipoTelefonePorCodigo("CELULAR");

        if(numero.length() == COMPRIMENTO_NUMERO_RESIDENCIAL && tipoTelefone.getId().equals(tipoTelefoneCelular.getId())){
            throw new RegraDeNegocioException("Você está inserindo um telefone para o tipo de telefone como celular");
        }

        TipoTelefone tipoTelefoneResidencial = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");
        if(numero.length() == COMPRIMENTO_NUMERO_CELULAR && tipoTelefone.getId().equals(tipoTelefoneResidencial.getId())){
            throw new RegraDeNegocioException("Você está inserindo um celular para o tipo de telefone como residencial");
        }
    }

    private List<Telefone> buscarTelefones(Long idUsuario){
        try {
            return telefoneDAO.buscarTelefonesPorIdUsuario(idUsuario);
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o telefones por id usuário, {}", e);
            throw new ErroException(e.getMessage());
        }
    }
}
