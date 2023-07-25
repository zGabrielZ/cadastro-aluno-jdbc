package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.GeneroDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.dto.GeneroViewDTO;
import lombok.AllArgsConstructor;

import static br.com.gabrielferreira.modelo.dto.factory.GeneroDTOFactory.*;
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
