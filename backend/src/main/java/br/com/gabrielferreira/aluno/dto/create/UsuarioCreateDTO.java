package br.com.gabrielferreira.aluno.dto.create;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
public record UsuarioCreateDTO(
        String nome,
        String email,
        String senha,
        LocalDate dataNascimento,
        String cpf,
        String nomeSocial,
        Long idGenero,
        Long idPerfil,
        List<TelefoneCreateDTO> telefones
) implements Serializable {

    @Override
    public List<TelefoneCreateDTO> telefones() {
        return telefones == null ? new ArrayList<>() : telefones;
    }
}
