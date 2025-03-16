package br.com.gabrielferreira.aluno.service;

import br.com.gabrielferreira.aluno.dto.UsuarioDTO;
import br.com.gabrielferreira.aluno.dto.create.UsuarioCreateDTO;
import br.com.gabrielferreira.aluno.dto.update.UsuarioUpdateDTO;

public interface UsuarioService {

    UsuarioDTO inserir(UsuarioCreateDTO usuarioCreateDTO);

    UsuarioDTO buscarPorId(Long id);

    UsuarioDTO atualizar(UsuarioUpdateDTO usuarioUpdateDTO, Long id);

    void deletarPorId(Long id);

    void deletarTelefonesPorIdUsuario(Long id);

    void deletarTudo();
}
