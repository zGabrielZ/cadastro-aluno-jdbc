package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.PerfilDAO;
import br.com.gabrielferreira.dto.PerfilDTO;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.Perfil;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.dto.factory.PerfilDTOFactory.toPerfilDTO;
import static br.com.gabrielferreira.utils.LogUtils.gerarLogWarn;


@AllArgsConstructor
public class PerfilService {

    private PerfilDAO perfilDAO;

    public PerfilDTO buscarPorId(Long id){
        Perfil perfil = buscarPerfilPorId(id);
        return toPerfilDTO(perfil);
    }

    public Perfil buscarPerfilPorCodigo(String codigo){
        try {
            Perfil perfil = perfilDAO.buscarPerfilPorCodigo(codigo);
            if(perfil == null){
                throw new ErroException("Perfil não encontrado");
            }
            return perfil;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o perfil, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }

    public Perfil buscarPerfilPorId(Long id){
        try {
            Perfil perfil = perfilDAO.buscarPorId(id);
            if(perfil == null){
                throw new ErroException("Perfil não encontrado");
            }
            return perfil;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o perfil, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }
}
