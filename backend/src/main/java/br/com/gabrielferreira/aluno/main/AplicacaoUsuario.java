package br.com.gabrielferreira.aluno.main;

import br.com.gabrielferreira.aluno.dao.*;
import br.com.gabrielferreira.aluno.dao.factory.DaoFactory;
import br.com.gabrielferreira.aluno.dto.UsuarioDTO;
import br.com.gabrielferreira.aluno.dto.create.TelefoneCreateDTO;
import br.com.gabrielferreira.aluno.dto.create.UsuarioCreateDTO;
import br.com.gabrielferreira.aluno.exception.ErroException;
import br.com.gabrielferreira.aluno.model.Genero;
import br.com.gabrielferreira.aluno.model.Perfil;
import br.com.gabrielferreira.aluno.model.TipoTelefone;
import br.com.gabrielferreira.aluno.service.*;
import br.com.gabrielferreira.aluno.service.factory.ServiceFactory;
import lombok.Generated;

import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.gabrielferreira.aluno.utils.LogUtils.gerarLogWarn;

@Generated
public class AplicacaoUsuario {

    private static final String MSG = "----------------------------------------------";

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.setProperty("AMBIENTE", "DEV");

        TipoTelefoneDAO tipoTelefoneDAO = DaoFactory.criarTipoTelefoneDao();
        TipoTelefoneService tipoTelefoneService = ServiceFactory.criarTipoTelefoneService(tipoTelefoneDAO);

        TelefoneDAO telefoneDAO = DaoFactory.criarTelefoneDao();
        TelefoneService telefoneService = ServiceFactory.criarTelefoneService(telefoneDAO, tipoTelefoneDAO);

        GeneroDAO generoDAO = DaoFactory.criarGeneroDao();
        GeneroService generoService = ServiceFactory.criarGeneroService(generoDAO);

        UsuarioDAO usuarioDAO = DaoFactory.criarUsuarioDao();
        UsuarioService usuarioService = ServiceFactory.criarUsuarioService(usuarioDAO, telefoneDAO, tipoTelefoneDAO, generoDAO);

        PerfilDAO perfilDAO = DaoFactory.criarPerfilDao();
        PerfilService perfilService = ServiceFactory.criarPerfilService(perfilDAO);

        Genero generoMasculino = generoService.buscarGeneroPorCodigo("MASCULINO");
        Genero generoFeminino = generoService.buscarGeneroPorCodigo("FEMININO");

        Perfil perfilAdmin = perfilService.buscarPerfilPorCodigo("ADMINISTRADOR");
        Perfil perfilAluno = perfilService.buscarPerfilPorCodigo("ALUNO");
        Perfil perfilProfessor = perfilService.buscarPerfilPorCodigo("PROFESSOR");

        TipoTelefone tipoTelefoneResidencial = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");
        TipoTelefone tipoTelefoneCelular = tipoTelefoneService.buscarTipoTelefonePorCodigo("CELULAR");

        gerarDelecaoBase(usuarioService, telefoneService);

        UsuarioDTO usuario1 = gerarPrimeiroUsuario(generoMasculino, perfilAdmin, usuarioService);

        System.out.println(MSG);

        UsuarioDTO usuario2 = gerarSegundoUsuario(generoFeminino, perfilAluno, usuarioService);

        System.out.println(MSG);

        UsuarioDTO usuario3 = gerarTerceiroUsuario(perfilProfessor, usuarioService);

        System.out.println(MSG);

        UsuarioDTO usuario4 = gerarQuartoUsuario(perfilProfessor, usuarioService);

        System.out.println(MSG);

        UsuarioDTO usuario5 = gerarQuintoUsuario(perfilAluno, generoFeminino, tipoTelefoneResidencial, tipoTelefoneCelular, usuarioService);

        System.out.println(MSG);

        gerarDelecaoUsuarios(Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5), usuarioService);
    }

    private static void gerarDelecaoBase(UsuarioService usuarioService, TelefoneService telefoneService){
        System.out.println("Gerar deleção base");
        telefoneService.deletarTudo();
        usuarioService.deletarTudo();
        System.out.println("Base limpa com sucesso");
    }

    private static UsuarioDTO gerarPrimeiroUsuario(Genero generoMasculino, Perfil perfilAdmin, UsuarioService usuarioService){
        UsuarioCreateDTO usuarioCreateDTO = UsuarioCreateDTO.builder()
                .nome("José da Silva")
                .email("jose@email.com")
                .senha("Jos123@")
                .dataNascimento(LocalDate.of(1995, 10, 20))
                .cpf("75582378073")
                .nomeSocial(null)
                .idGenero(generoMasculino.getId())
                .idPerfil(perfilAdmin.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioDTO usuarioResultado = usuarioService.inserir(usuarioCreateDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.id(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioDTO gerarSegundoUsuario(Genero generoFeminino, Perfil perfilAluno, UsuarioService usuarioService){
        UsuarioCreateDTO usuarioCreateDTO = UsuarioCreateDTO.builder()
                .nome("Mariana da Silva")
                .email("mari@email.com")
                .senha("Mari123@")
                .dataNascimento(LocalDate.of(1997, 3, 14))
                .cpf("87213966049")
                .nomeSocial(null)
                .idGenero(generoFeminino.getId())
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioDTO usuarioResultado = usuarioService.inserir(usuarioCreateDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.id(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioDTO gerarTerceiroUsuario(Perfil perfilProfessor, UsuarioService usuarioService){
        UsuarioCreateDTO usuarioCreateDTO = UsuarioCreateDTO.builder()
                .nome("Marcos da Silva")
                .email("marcos@email.com")
                .senha("Marcos3455@")
                .dataNascimento(LocalDate.of(1990, 5, 4))
                .cpf("35404423080")
                .nomeSocial("Marcos nome social")
                .idGenero(null)
                .idPerfil(perfilProfessor.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioDTO usuarioResultado = usuarioService.inserir(usuarioCreateDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.id(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioDTO gerarQuartoUsuario(Perfil perfilProfessor, UsuarioService usuarioService){
        UsuarioCreateDTO usuarioCreateDTO = UsuarioCreateDTO.builder()
                .nome("Leandro da Silva")
                .email("leandro@email.com")
                .senha("Lasddf4566@#")
                .dataNascimento(LocalDate.of(1984, 11, 11))
                .cpf("97892034030")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilProfessor.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioDTO usuarioResultado = usuarioService.inserir(usuarioCreateDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.id(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioDTO gerarQuintoUsuario(Perfil perfilAluno, Genero generoFeminino, TipoTelefone tipoTelefoneResidencial,
                                                 TipoTelefone tipoTelefoneCelular, UsuarioService usuarioService){
        List<TelefoneCreateDTO> telefoneCreateDTOS = new ArrayList<>();
        telefoneCreateDTOS.add(TelefoneCreateDTO.builder().ddd("11").numero("34421812").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
        telefoneCreateDTOS.add(TelefoneCreateDTO.builder().ddd("11").numero("31242526").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
        telefoneCreateDTOS.add(TelefoneCreateDTO.builder().ddd("11").numero("987685822").idTipoTelefone(tipoTelefoneCelular.getId()).build());

        UsuarioCreateDTO usuarioCreateDTO = UsuarioCreateDTO.builder()
                .nome("Joana da Silva")
                .email("joana@email.com")
                .senha("Joanahndfni123@#@")
                .dataNascimento(LocalDate.of(2000, 6, 6))
                .cpf("42378371063")
                .nomeSocial(null)
                .idGenero(generoFeminino.getId())
                .idPerfil(perfilAluno.getId())
                .telefones(telefoneCreateDTOS)
                .build();

        UsuarioDTO usuarioResultado = usuarioService.inserir(usuarioCreateDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.id(), usuarioService);
        return usuarioResultado;
    }

    private static void imprimirDadosUsuarioEncontrado(Long idUsuario, UsuarioService usuarioService){
        UsuarioDTO usuarioEncontrado = usuarioService.buscarPorId(idUsuario);
        System.out.println("Dados do usuário do ID " + usuarioEncontrado.id());

        String cpfFormatado = toCpfFormatado(usuarioEncontrado.cpf());
        String nomeSocial = usuarioEncontrado.nomeSocial() != null ? usuarioEncontrado.nomeSocial() : "";
        String genero = usuarioEncontrado.genero() != null ? usuarioEncontrado.genero().descricao() : "";

        System.out.println("Nome : " + usuarioEncontrado.nome());
        System.out.println("E-mail : " + usuarioEncontrado.email());
        System.out.println("Data de Nascimento : " + DATA_FORMATTER.format(usuarioEncontrado.dataNascimento()));
        System.out.println("CPF : " + cpfFormatado);
        System.out.println("Nome social : " + nomeSocial);
        System.out.println("Gênero : " + genero);
        System.out.println("Perfil : " + usuarioEncontrado.perfil().descricao());

        System.out.println("Telefones : ");
        if(!usuarioEncontrado.telefones().isEmpty()){
            usuarioEncontrado.telefones().forEach(telefone -> System.out.println(telefone.ddd() + " - " + telefone.numero() + " - " + telefone.tipoTelefone().descricao()));
        } else {
            System.out.println("Nenhum telefone cadastrado para este usuário " + usuarioEncontrado.nome());
        }
    }

    private static void gerarDelecaoUsuarios(List<UsuarioDTO> usuarios, UsuarioService usuarioService){
        System.out.println("Deletando os usuários");
        usuarios.forEach(usuario -> {
            usuarioService.deletarTelefonesPorIdUsuario(usuario.id());
            usuarioService.deletarPorId(usuario.id());
        });
    }

    private static String toCpfFormatado(String cpf){
        try {
            MaskFormatter cpfFormatacao = new MaskFormatter("###.###.###-##");
            cpfFormatacao.setValueContainsLiteralCharacters(false);
            return cpfFormatacao.valueToString(cpf);
        } catch (Exception e){
            gerarLogWarn("Erro ao colocar a máscara cpf {}", e);
            throw new ErroException("Erro ao colocar a máscara");
        }
    }
}
