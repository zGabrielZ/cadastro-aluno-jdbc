package br.com.gabrielferreira.controller;

import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.service.TipoTelefoneService;

import java.io.Serializable;
import java.util.List;

public class TipoTelefoneController implements Serializable {

    private static final long serialVersionUID = -517437237911854208L;

    private final TipoTelefoneService tipoTelefoneService;

    public TipoTelefoneController(TipoTelefoneService tipoTelefoneService){
        this.tipoTelefoneService = tipoTelefoneService;
    }

    public List<TipoTelefone> buscarTiposTelefones(){
        return tipoTelefoneService.listaDeTipoTelefones();
    }

    public TipoTelefone buscarTipoTelefonePorId(Long id){
        return tipoTelefoneService.buscarTipoTelefonePorId(id);
    }
}
