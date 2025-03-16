package br.com.gabrielferreira.aluno.service.impl;

import br.com.gabrielferreira.aluno.dao.GeneroDAO;
import br.com.gabrielferreira.aluno.dto.GeneroDTO;
import br.com.gabrielferreira.aluno.exception.ErroException;
import br.com.gabrielferreira.aluno.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.aluno.model.Genero;
import br.com.gabrielferreira.aluno.service.GeneroService;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.aluno.dto.factory.GeneroDTOFactory.toGeneroDTO;
import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

@AllArgsConstructor
public class GeneroServiceImpl implements GeneroService {

    private GeneroDAO generoDAO;

    @Override
    public GeneroDTO buscarPorId(Long id) {
        Genero genero = buscarGeneroPorId(id);
        return toGeneroDTO(genero);
    }

    @Override
    public Genero buscarGeneroPorCodigo(String codigo) {
        try {
            Genero genero = generoDAO.buscarGeneroPorCodigo(codigo);
            if(genero == null){
                throw new ErroException("Gênero não encontrado");
            }
            return genero;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o gênero, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }

    @Override
    public Genero buscarGeneroPorId(Long id) {
        try {
            Genero genero = generoDAO.buscarPorId(id);
            if(genero == null){
                throw new ErroException("Gênero não encontrado");
            }
            return genero;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o gênero, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }
}
