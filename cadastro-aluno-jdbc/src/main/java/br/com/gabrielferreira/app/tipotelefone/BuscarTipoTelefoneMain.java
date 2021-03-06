package br.com.gabrielferreira.app.tipotelefone;

import br.com.gabrielferreira.controller.TipoTelefoneController;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.service.TipoTelefoneService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuscarTipoTelefoneMain {

    public static void main(String[] args) {
        TipoTelefoneService tipoTelefoneService = new TipoTelefoneService();
        TipoTelefoneController tipoTelefoneController = new TipoTelefoneController(tipoTelefoneService);
        TipoTelefone tipoTelefone = tipoTelefoneController.buscarTipoTelefonePorId(1L);
        log.info("ID : {}, DESCRIÇÃO : {}",tipoTelefone.getId(),tipoTelefone.getDescricao());
    }
}
