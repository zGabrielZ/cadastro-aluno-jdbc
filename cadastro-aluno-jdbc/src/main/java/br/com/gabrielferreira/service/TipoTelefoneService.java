package br.com.gabrielferreira.service;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.dao.TipoTelefoneDAO;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.TipoTelefone;

import java.io.Serializable;
import java.util.List;

public class TipoTelefoneService implements Serializable {

    private static final long serialVersionUID = -4873911623755365754L;

    private final TipoTelefoneDAO tipoTelefoneDAO;

    public TipoTelefoneService(){
        tipoTelefoneDAO = new TipoTelefoneDAO(ConexaoBD.getConnection());
    }

    public List<TipoTelefone> listaDeTipoTelefones(){
        return tipoTelefoneDAO.listaDeTipoTelefones();
    }

    public TipoTelefone buscarTipoTelefonePorId(Long id){
        TipoTelefone tipoTelefone = tipoTelefoneDAO.buscarPorId(id);
        if(tipoTelefone.getId() == null){
            throw new RegraDeNegocioException("Tipo de telefone não encontrado.");
        }
        return tipoTelefone;
    }

}
