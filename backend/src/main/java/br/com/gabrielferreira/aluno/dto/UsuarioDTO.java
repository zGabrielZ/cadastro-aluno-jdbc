package br.com.gabrielferreira.aluno.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        String nomeSocial,
        GeneroDTO genero,
        PerfilDTO perfil,
        List<TelefoneDTO> telefones
) implements Serializable {

    @Override
    public List<TelefoneDTO> telefones() {
        return telefones == null ? new ArrayList<>() : telefones;
    }
}
