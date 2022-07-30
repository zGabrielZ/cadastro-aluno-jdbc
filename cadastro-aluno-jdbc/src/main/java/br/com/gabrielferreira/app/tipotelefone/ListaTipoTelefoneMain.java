package br.com.gabrielferreira.app.tipotelefone;

import br.com.gabrielferreira.controller.TipoTelefoneController;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.service.TipoTelefoneService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ListaTipoTelefoneMain {

    public static void main(String[] args) {
        TipoTelefoneService tipoTelefoneService = new TipoTelefoneService();
        TipoTelefoneController tipoTelefoneController = new TipoTelefoneController(tipoTelefoneService);
        List<TipoTelefone> tipoTelefones = tipoTelefoneController.buscarTiposTelefones();
        for(TipoTelefone tipoTelefone : tipoTelefones){
            log.info("ID : {}, DESCRIÇÃO : {}",tipoTelefone.getId(),tipoTelefone.getDescricao());
        }
    }
}
