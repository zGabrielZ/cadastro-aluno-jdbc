package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.TipoTelefoneDAO;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.TipoTelefone;
import br.com.gabrielferreira.dto.TipoTelefoneViewDTO;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.factory.dto.TipoTelefoneDTOFactory.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

@AllArgsConstructor
public class TipoTelefoneService {

    private TipoTelefoneDAO tipoTelefoneDAO;

    public TipoTelefoneViewDTO buscarPorId(Long id){
        TipoTelefone tipoTelefone = buscarTipoTelefonePorId(id);
        return toTipoTelefoneViewDTO(tipoTelefone);
    }

    public TipoTelefone buscarTipoTelefonePorCodigo(String codigo){
        try {
            TipoTelefone tipoTelefone = tipoTelefoneDAO.buscarTipoTelefonePorCodigo(codigo);
            if(tipoTelefone == null){
                throw new ErroException("Tipo de telefone não encontrado");
            }
            return tipoTelefone;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o tipo de telefone, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }

    public TipoTelefone buscarTipoTelefonePorId(Long id){
        try {
            TipoTelefone tipoTelefone = tipoTelefoneDAO.buscarPorId(id);
            if(tipoTelefone == null){
                throw new ErroException("Tipo de telefone não encontrado");
            }
            return tipoTelefone;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o tipo de telefone, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }
}
