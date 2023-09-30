package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.PerfilDAO;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.Perfil;
import br.com.gabrielferreira.dto.view.PerfilViewDTO;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.factory.dto.PerfilDTOFactory.*;
import static br.com.gabrielferreira.utils.LogUtils.*;


@AllArgsConstructor
public class PerfilService {

    private PerfilDAO perfilDAO;

    public PerfilViewDTO buscarPorId(Long id){
        Perfil perfil = buscarPerfilPorId(id);
        return toPerfilViewDTO(perfil);
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
