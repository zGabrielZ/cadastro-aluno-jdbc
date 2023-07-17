//package br.com.gabrielferreira.service;
//
//import br.com.gabrielferreira.dao.AlunoDAO;
//import br.com.gabrielferreira.dao.EmailDAO;
//import br.com.gabrielferreira.dao.TelefoneDAO;
//import br.com.gabrielferreira.dao.TipoTelefoneDAO;
//import br.com.gabrielferreira.modelo.Aluno;
//import br.com.gabrielferreira.modelo.Email;
//import br.com.gabrielferreira.modelo.Telefone;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.Serializable;
//
//@Slf4j
//@RequiredArgsConstructor
//public class AlunoService implements Serializable {
//
//    private static final long serialVersionUID = 6730307453958143191L;
//
//    private AlunoDAO alunoDAO;
//
//    private EmailDAO emailDAO;
//
//    private TelefoneDAO telefoneDAO;
//
//    private TipoTelefoneDAO tipoTelefoneDAO;
//
//    public Aluno inserir(Aluno aluno){
//        try {
//            Email emailInserido = emailDAO.inserir(aluno.getEmail());
//            Aluno alunoInserido = alunoDAO.inserir(aluno);
//            aluno.getTelefones().forEach(telefone -> {
//                inserirTelefone(telefone);
//            });
//        } catch (Exception e){
//            log.warn("Erro : {}",e.getMessage());
//        }
//        return aluno;
//    }
//
//    public Telefone inserirTelefone(Telefone telefone){
//        try {
//            telefoneDAO.inserir(telefone);
//        } catch (Exception e){
//            log.warn("Erro : {}",e.getMessage());
//        }
//        return telefone;
//    }
//}
