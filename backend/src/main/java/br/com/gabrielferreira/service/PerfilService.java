package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.PerfilDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.Perfil;
import br.com.gabrielferreira.modelo.dto.PerfilViewDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;

import static br.com.gabrielferreira.modelo.dto.factory.PerfilDTOFactory.*;

@Slf4j
@AllArgsConstructor
public class PerfilService implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

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
            log.warn("Erro ao buscar o perfil, {}", e.getMessage());
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
            log.warn("Erro ao buscar o perfil, {}", e.getMessage());
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }
}
