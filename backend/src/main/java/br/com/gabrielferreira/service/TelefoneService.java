package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.TelefoneDAO;
import br.com.gabrielferreira.dto.TelefoneDTO;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.exception.RegraDeNegocioException;
import br.com.gabrielferreira.model.Telefone;
import br.com.gabrielferreira.model.TipoTelefone;
import lombok.AllArgsConstructor;

import java.util.List;

import static br.com.gabrielferreira.dto.factory.TelefoneDTOFactory.toTelefonesDTO;
import static br.com.gabrielferreira.utils.LogUtils.gerarLogWarn;

@AllArgsConstructor
public class TelefoneService {

    private static final Long COMPRIMENTO_NUMERO_RESIDENCIAL = 8L;
    private static final Long COMPRIMENTO_NUMERO_CELULAR = 9L;

    private TelefoneDAO telefoneDAO;

    private TipoTelefoneService tipoTelefoneService;

    public List<TelefoneDTO> buscarTelefonesPorIdUsuario(Long idUsuario){
        List<Telefone> telefones = buscarTelefones(idUsuario);
        return toTelefonesDTO(telefones);
    }

    public void deletarTudo(){
        try {
            telefoneDAO.deleteTudo();
        } catch (Exception e){
            gerarLogWarn("Erro ao deletar tudo no telefone, {}", e);
            throw new ErroException("Erro ao deletar tudo no telefone, tente mais tarde.");
        }
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
