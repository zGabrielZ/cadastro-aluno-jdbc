package br.com.gabrielferreira.aluno.service;

import br.com.gabrielferreira.aluno.dao.TipoTelefoneDAO;
import br.com.gabrielferreira.aluno.dto.TipoTelefoneDTO;
import br.com.gabrielferreira.aluno.exception.ErroException;
import br.com.gabrielferreira.aluno.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.aluno.model.TipoTelefone;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.aluno.dto.factory.TipoTelefoneDTOFactory.toTipoTelefoneDTO;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

@AllArgsConstructor
public class TipoTelefoneService {

    private TipoTelefoneDAO tipoTelefoneDAO;

    public TipoTelefoneDTO buscarPorId(Long id){
        TipoTelefone tipoTelefone = buscarTipoTelefonePorId(id);
        return toTipoTelefoneDTO(tipoTelefone);
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
