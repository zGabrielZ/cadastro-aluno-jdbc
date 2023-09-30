package br.com.gabrielferreira.main;

import br.com.gabrielferreira.conexao.ConexaoBD;
import br.com.gabrielferreira.conexao.config.ConfigBandoDeDadosDevImpl;
import br.com.gabrielferreira.dao.*;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.model.Genero;
import br.com.gabrielferreira.model.Perfil;
import br.com.gabrielferreira.model.TipoTelefone;
import br.com.gabrielferreira.model.dto.TelefoneDTO;
import br.com.gabrielferreira.model.dto.UsuarioDTO;
import br.com.gabrielferreira.model.dto.UsuarioViewDTO;
import br.com.gabrielferreira.service.*;
import lombok.Generated;

import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static br.com.gabrielferreira.utils.LogUtils.*;

@Generated
public class AplicacaoUsuario {

    private static final String MSG = "----------------------------------------------";

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        ConexaoBD conexaoBD = new ConexaoBD(new ConfigBandoDeDadosDevImpl());
        TipoTelefoneDAO tipoTelefoneDAO = new TipoTelefoneDAO(conexaoBD.getConnection());
        TipoTelefoneService tipoTelefoneService = new TipoTelefoneService(tipoTelefoneDAO);

        TelefoneDAO telefoneDAO = new TelefoneDAO(conexaoBD.getConnection());
        TelefoneService telefoneService = new TelefoneService(telefoneDAO, tipoTelefoneService);

        GeneroDAO generoDAO = new GeneroDAO(conexaoBD.getConnection());
        GeneroService generoService = new GeneroService(generoDAO);

        UsuarioDAO usuarioDAO = new UsuarioDAO(conexaoBD.getConnection(), telefoneDAO);
        UsuarioService usuarioService = new UsuarioService(usuarioDAO, telefoneService, generoService);

        PerfilDAO perfilDAO = new PerfilDAO(conexaoBD.getConnection());
        PerfilService perfilService = new PerfilService(perfilDAO);

        Genero generoMasculino = generoService.buscarGeneroPorCodigo("MASCULINO");
        Genero generoFeminino = generoService.buscarGeneroPorCodigo("FEMININO");

        Perfil perfilAdmin = perfilService.buscarPerfilPorCodigo("ADMINISTRADOR");
        Perfil perfilAluno = perfilService.buscarPerfilPorCodigo("ALUNO");
        Perfil perfilProfessor = perfilService.buscarPerfilPorCodigo("PROFESSOR");

        TipoTelefone tipoTelefoneResidencial = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");
        TipoTelefone tipoTelefoneCelular = tipoTelefoneService.buscarTipoTelefonePorCodigo("CELULAR");

        gerarDelecaoBase(usuarioService, telefoneService);

        UsuarioViewDTO usuario1 = gerarPrimeiroUsuario(generoMasculino, perfilAdmin, usuarioService);

        System.out.println(MSG);

        UsuarioViewDTO usuario2 = gerarSegundoUsuario(generoFeminino, perfilAluno, usuarioService);

        System.out.println(MSG);

        UsuarioViewDTO usuario3 = gerarTerceiroUsuario(perfilProfessor, usuarioService);

        System.out.println(MSG);

        UsuarioViewDTO usuario4 = gerarQuartoUsuario(perfilProfessor, usuarioService);

        System.out.println(MSG);

        UsuarioViewDTO usuario5 = gerarQuintoUsuario(perfilAluno, generoFeminino, tipoTelefoneResidencial, tipoTelefoneCelular, usuarioService);

        System.out.println(MSG);

        gerarDelecaoUsuarios(Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5), usuarioService);
    }

    private static void gerarDelecaoBase(UsuarioService usuarioService, TelefoneService telefoneService){
        System.out.println("Gerar deleção base");
        telefoneService.deletarTudo();
        usuarioService.deletarTudo();
        System.out.println("Base limpa com sucesso");
    }

    private static UsuarioViewDTO gerarPrimeiroUsuario(Genero generoMasculino, Perfil perfilAdmin, UsuarioService usuarioService){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
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

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.getId(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioViewDTO gerarSegundoUsuario(Genero generoFeminino, Perfil perfilAluno, UsuarioService usuarioService){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
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

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.getId(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioViewDTO gerarTerceiroUsuario(Perfil perfilProfessor, UsuarioService usuarioService){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
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

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.getId(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioViewDTO gerarQuartoUsuario(Perfil perfilProfessor, UsuarioService usuarioService){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
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

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.getId(), usuarioService);
        return usuarioResultado;
    }

    private static UsuarioViewDTO gerarQuintoUsuario(Perfil perfilAluno, Genero generoFeminino, TipoTelefone tipoTelefoneResidencial, TipoTelefone tipoTelefoneCelular, UsuarioService usuarioService){
        List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
        telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("34421812").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
        telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("31242526").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
        telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("987685822").idTipoTelefone(tipoTelefoneCelular.getId()).build());

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Joana da Silva")
                .email("joana@email.com")
                .senha("Joanahndfni123@#@")
                .dataNascimento(LocalDate.of(2000, 6, 6))
                .cpf("42378371063")
                .nomeSocial(null)
                .idGenero(generoFeminino.getId())
                .idPerfil(perfilAluno.getId())
                .telefones(telefoneDTOS)
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        imprimirDadosUsuarioEncontrado(usuarioResultado.getId(), usuarioService);
        return usuarioResultado;
    }

    private static void imprimirDadosUsuarioEncontrado(Long idUsuario, UsuarioService usuarioService){
        UsuarioViewDTO usuarioEncontrado = usuarioService.buscarPorId(idUsuario);
        System.out.println("Dados do usuário do ID " + usuarioEncontrado.getId());

        String cpfFormatado = toCpfFormatado(usuarioEncontrado.getCpf());
        String nomeSocial = usuarioEncontrado.getNomeSocial() != null ? usuarioEncontrado.getNomeSocial() : "";
        String genero = usuarioEncontrado.getGenero() != null ? usuarioEncontrado.getGenero().getDescricao() : "";

        System.out.println("Nome : " + usuarioEncontrado.getNome());
        System.out.println("E-mail : " + usuarioEncontrado.getEmail());
        System.out.println("Data de Nascimento : " + DATA_FORMATTER.format(usuarioEncontrado.getDataNascimento()));
        System.out.println("CPF : " + cpfFormatado);
        System.out.println("Nome social : " + nomeSocial);
        System.out.println("Gênero : " + genero);
        System.out.println("Perfil : " + usuarioEncontrado.getPerfil().getDescricao());

        System.out.println("Telefones : ");
        if(!usuarioEncontrado.getTelefones().isEmpty()){
            usuarioEncontrado.getTelefones().forEach(telefone -> System.out.println(telefone.getDdd() + " - " + telefone.getNumero() + " - " + telefone.getTipoTelefone().getDescricao()));
        } else {
            System.out.println("Nenhum telefone cadastrado para este usuário " + usuarioEncontrado.getNome());
        }
    }

    private static void gerarDelecaoUsuarios(List<UsuarioViewDTO> usuarios, UsuarioService usuarioService){
        System.out.println("Deletando os usuários");
        usuarios.forEach(usuario -> {
            usuarioService.deletarTelefonesPorIdUsuario(usuario.getId());
            usuarioService.deletarPorId(usuario.getId());
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
