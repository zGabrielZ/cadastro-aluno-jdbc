package br.com.gabrielferreira.aluno.service;

import br.com.gabrielferreira.aluno.dto.TelefoneDTO;
import br.com.gabrielferreira.aluno.model.TipoTelefone;

import java.util.List;

public interface TelefoneService {

    List<TelefoneDTO> buscarTelefonesPorIdUsuario(Long idUsuario);

    void deletarTudo();

    void validarTipoTelefoneComNumero(String numero, TipoTelefone tipoTelefone);
}
