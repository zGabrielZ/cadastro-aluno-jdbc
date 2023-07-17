//package br.com.gabrielferreira.service;
//import br.com.gabrielferreira.dao.TelefoneDAO;
//import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
//import br.com.gabrielferreira.modelo.Telefone;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//public class TelefoneService implements Serializable {
//
//    private static final long serialVersionUID = 6730307453958143191L;
//
//    private TelefoneDAO telefoneDAO;
//
//    public Telefone inserir(Telefone telefone){
//        try {
//            verificarTelefone(telefone);
//
////            String endereco = Telefone.getEndereco().trim();
////            validarTelefoneValido(endereco);
////            verificarTelefoneCadastrado(endereco);
////            TelefoneDAO.inserir(Telefone);
//        } catch (Exception e){
//            log.warn("Erro : {}",e.getMessage());
//        }
//        return telefone;
//    }
//
//    private List<String> verificarTelefone(Telefone telefone){
//        List<String> erros = new ArrayList<>();
//        if(telefone.getDdd() == null && telefone.getDdd().isBlank()){
//            erros.add("DDD do telefone é obrigatório.");
//        }
//
//        if(telefone.getNumero() == null && telefone.getNumero().isBlank()){
//            erros.add("Número do telefone é obrigatório.");
//        }
//
//        return erros;
//    }
//
////    private void validarTelefoneValido(String endereco){
////        String regex = "^(.+)@(.+)$";
////        Pattern pattern = Pattern.compile(regex);
////
////        if(!pattern.matcher(endereco).matches()){
////            throw new RegraDeNegocioException("Digite um endereço do e-mail válido.");
////        }
////    }
////
////    private void verificarTelefoneCadastrado(String endereco){
////        if(TelefoneDAO.checarTelefoneCadastrado(endereco)){
////            throw new RegraDeNegocioException("Este e-mail já foi cadastrado.");
////        }
////    }
//}
