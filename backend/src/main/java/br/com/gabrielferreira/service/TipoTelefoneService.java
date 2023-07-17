//package br.com.gabrielferreira.service;
//import br.com.gabrielferreira.dao.TipoTelefoneDAO;
//import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
//import br.com.gabrielferreira.modelo.TipoTelefone;
//import lombok.RequiredArgsConstructor;
//
//import java.io.Serializable;
//import java.util.List;
//
//@RequiredArgsConstructor
//public class TipoTelefoneService implements Serializable {
//
//    private static final long serialVersionUID = -4873911623755365754L;
//
//    private final TipoTelefoneDAO tipoTelefoneDAO;
//
//    public List<TipoTelefone> listaDeTipoTelefones(){
//        return tipoTelefoneDAO.listaDeTipoTelefones();
//    }
//
//    public TipoTelefone buscarTipoTelefonePorId(Long id){
//        TipoTelefone tipoTelefone = tipoTelefoneDAO.buscarPorId(id);
//        if(!buscarTipoTelefone(tipoTelefone)){
//            throw new RegraDeNegocioException("Tipo de telefone não encontrado.");
//        }
//        return tipoTelefone;
//    }
//
//    private boolean buscarTipoTelefone(TipoTelefone tipoTelefone){
//        return tipoTelefone != null && tipoTelefone.getId() != null;
//    }
//
//}
