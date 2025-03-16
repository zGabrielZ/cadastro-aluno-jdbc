package br.com.gabrielferreira.aluno.service;

import br.com.gabrielferreira.aluno.dto.TipoTelefoneDTO;
import br.com.gabrielferreira.aluno.model.TipoTelefone;

public interface TipoTelefoneService {

    TipoTelefoneDTO buscarPorId(Long id);

    TipoTelefone buscarTipoTelefonePorCodigo(String codigo);

    TipoTelefone buscarTipoTelefonePorId(Long id);
}
