package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.GeneroDAO;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.model.Genero;
import br.com.gabrielferreira.dto.GeneroViewDTO;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.factory.dto.GeneroDTOFactory.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

@AllArgsConstructor
public class GeneroService {

    private GeneroDAO generoDAO;

    public GeneroViewDTO buscarPorId(Long id){
        Genero genero = buscarGeneroPorId(id);
        return toGeneroViewDTO(genero);
    }

    public Genero buscarGeneroPorCodigo(String codigo){
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

    public Genero buscarGeneroPorId(Long id){
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
