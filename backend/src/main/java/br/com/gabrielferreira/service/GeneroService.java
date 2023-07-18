package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.GeneroDAO;
import br.com.gabrielferreira.exceptions.ErroException;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.dto.GeneroViewDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;

import static br.com.gabrielferreira.modelo.dto.factory.GeneroDTOFactory.*;

@Slf4j
@AllArgsConstructor
public class GeneroService implements Serializable {

    @Serial
    private static final long serialVersionUID = 6730307453958143191L;

    private GeneroDAO generoDAO;

    public GeneroViewDTO buscarPorId(Long id){
        Genero genero;
        try {
           genero = buscarGeneroPorId(id);
        } catch (Exception e){
            log.warn("Erro ao buscar o gênero, {}", e.getMessage());
            throw new ErroException("Erro ao buscar o gênero, tente mais tarde.");
        }

        return toGeneroViewDTO(genero);
    }

    public Genero buscarGeneroPorId(Long id){
        Genero genero = generoDAO.buscarPorId(id);
        if(genero == null){
            throw new RegistroNaoEncontradoException("Gênero não encontrado");
        }
        return genero;
    }
}
