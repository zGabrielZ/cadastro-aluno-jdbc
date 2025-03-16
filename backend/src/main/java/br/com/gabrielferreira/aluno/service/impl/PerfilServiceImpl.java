package br.com.gabrielferreira.aluno.service.impl;

import br.com.gabrielferreira.aluno.dao.PerfilDAO;
import br.com.gabrielferreira.aluno.dto.PerfilDTO;
import br.com.gabrielferreira.aluno.exception.ErroException;
import br.com.gabrielferreira.aluno.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.aluno.model.Perfil;
import br.com.gabrielferreira.aluno.service.PerfilService;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.aluno.dto.factory.PerfilDTOFactory.toPerfilDTO;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

@AllArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private PerfilDAO perfilDAO;

    @Override
    public PerfilDTO buscarPorId(Long id) {
        Perfil perfil = buscarPerfilPorId(id);
        return toPerfilDTO(perfil);
    }

    @Override
    public Perfil buscarPerfilPorCodigo(String codigo) {
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

    @Override
    public Perfil buscarPerfilPorId(Long id) {
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
