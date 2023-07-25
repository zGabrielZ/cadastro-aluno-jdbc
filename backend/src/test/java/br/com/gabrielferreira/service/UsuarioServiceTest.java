package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.exceptions.RegistroNaoEncontradoException;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Perfil;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.dto.TelefoneDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    private UsuarioService usuarioService;

    private Genero generoMasculino;

    private Perfil perfilAluno;

    private TipoTelefone tipoTelefoneResidencial;

    private TipoTelefone tipoTelefoneCelular;

    @BeforeEach
    public void criarInstancias(){
        UsuarioDAO usuarioDAO = DaoFactory.criarUsuarioDao(TESTE);
        GeneroService generoService = new GeneroService(DaoFactory.criarGeneroDao(TESTE));
        TipoTelefoneService tipoTelefoneService = new TipoTelefoneService(DaoFactory.criarTipoTelefoneDao(TESTE));
        TelefoneService telefoneService = new TelefoneService(DaoFactory.criarTelefoneDao(TESTE), tipoTelefoneService);
        usuarioService = new UsuarioService(usuarioDAO, telefoneService);

        PerfilService perfilService = new PerfilService(DaoFactory.criarPerfilDao(TESTE));
        generoMasculino = generoService.buscarGeneroPorCodigo("MASCULINO");
        perfilAluno = perfilService.buscarPerfilPorCodigo("ALUNO");

        tipoTelefoneResidencial = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");
        tipoTelefoneCelular = tipoTelefoneService.buscarTipoTelefonePorCodigo("CELULAR");
    }

    @Test
    @DisplayName("Deve validar o nome quando for cadastrar o usuário")
    @Order(1)
    void deveValidarNomeCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome(null)
                    .email("teste@email.com")
                    .senha("Teste123#@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do nome nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o nome do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o tamanho do nome quando for cadastrar o usuário")
    @Order(2)
    void deveValidarNomeTamanhoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome(gerarStringGrande())
                    .email("teste@email.com")
                    .senha("Teste123#@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tamanho do nome");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o nome do usuário até 255 caracteres"));
        }
    }

    @Test
    @DisplayName("Deve validar o email quando for cadastrar o usuário")
    @Order(3)
    void deveValidarEmailCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email(null)
                    .senha("Teste123#@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do email nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o e-mail do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o tamanho do email quando for cadastrar o usuário")
    @Order(4)
    void deveValidarEmailTamanhoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email(gerarStringGrande())
                    .senha("Teste123#@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tamanho do email");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o e-mail do usuário até 255 caracteres"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"teste.com.br", "teste", "teste123.com", "teste123321email.com"})
    @DisplayName("Deve validar o email válido quando for cadastrar o usuário")
    @Order(5)
    void deveValidarEmailValidoCadastroUsuario(String email){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email(email)
                    .senha("Teste123#@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do email inválido");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Digite um endereço do e-mail válido para o usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar a senha quando for cadastrar o usuário")
    @Order(6)
    void deveValidarSenhaCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha(null)
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção da senha nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar a senha do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o tamanho da senha quando for cadastrar o usuário")
    @Order(7)
    void deveValidarSenhaTamanhoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha(gerarStringGrande())
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tamanho da senha");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar a senha do usuário até 20 caracteres"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"t1@", "T1@", "Tt@", "Tt1"})
    @DisplayName("Deve validar a senha fraca quando for cadastrar o usuário")
    @Order(8)
    void deveValidarSenhaFracaCadastroUsuario(String senha){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste@email.com")
                .senha(senha)
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial(null)
                .idGenero(generoMasculino.getId())
                .idPerfil(perfilAluno.getId())
                .build();
        assertThrows(RegraDeNegocioException.class, () -> usuarioService.inserir(usuarioDTO));
    }

    @Test
    @DisplayName("Deve validar a data de nascimento quando for cadastrar o usuário")
    @Order(9)
    void deveValidarDataNascimentoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Tt@123")
                    .dataNascimento(null)
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção da data de nascimento nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar a data de nascimento do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar a data de nascimento após data atual quando for cadastrar o usuário")
    @Order(10)
    void deveValidarDataNascimentoDepoisDataAtualCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Tt@123")
                    .dataNascimento(LocalDate.now().plusDays(10L))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção da data de nascimento depois da data atual");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("A data do nascimento informado é maior que a data atual"));
        }
    }

    @Test
    @DisplayName("Deve validar o cpf quando for cadastrar o usuário")
    @Order(11)
    void deveValidarCpfCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Tt@123")
                    .dataNascimento(LocalDate.of(2000, 10, 10))
                    .cpf(null)
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do cpf nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o CPF do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o cpf sem dígito quando for cadastrar o usuário")
    @Order(12)
    void deveValidarCpfSemDigitoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Tt@123")
                    .dataNascimento(LocalDate.of(2000, 10, 10))
                    .cpf("475A323900A")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do cpf sem dígito");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Digite o CPF com apenas dígitos do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o cpf inválido quando for cadastrar o usuário")
    @Order(13)
    void deveValidarCpfInvalidoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Tt@123")
                    .dataNascimento(LocalDate.of(2000, 10, 10))
                    .cpf("11111112222")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do cpf inválido");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("CPF do usuário informado é inválido"));
        }
    }

    @Test
    @DisplayName("Deve validar o tamanho do nome social quando for cadastrar o usuário")
    @Order(14)
    void deveValidarNomeSocialTamanhoCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(gerarStringGrande())
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tamanho do nome social");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o nome social do usuário até 255 caracteres"));
        }
    }

    @Test
    @DisplayName("Deve validar o perfil quando for cadastrar o usuário")
    @Order(15)
    void deveValidarPerfilCadastroUsuario(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(generoMasculino.getId())
                    .idPerfil(null)
                    .build();
            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do perfil nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o perfil do usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar cadastro de usuário quando ocorrer e-mail já cadastrado")
    @Order(16)
    void deveValidarCadastroUsuarioEmailRepetido(){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste@email.com")
                .senha("Teste123@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);

        UsuarioDTO usuarioDTO2 = UsuarioDTO.builder()
                .nome("Teste 12334")
                .email("teste@email.com")
                .senha("Teste1234@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("93504688084")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        try {
            usuarioService.inserir(usuarioDTO2);
            fail("Deveria ter lançado a exceção do email já cadastrado");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Este e-mail informado já foi cadastrado"));
        }

        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Deve validar cadastro de usuário quando ocorrer cpf já cadastrado")
    @Order(17)
    void deveValidarCadastroUsuarioCpfRepetido(){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste123@email.com")
                .senha("Teste123@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);

        UsuarioDTO usuarioDTO2 = UsuarioDTO.builder()
                .nome("Teste 12334")
                .email("teste1234@email.com")
                .senha("Teste1234@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        try {
            usuarioService.inserir(usuarioDTO2);
            fail("Deveria ter lançado a exceção do cpf já cadastrado");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Este CPF informado já foi cadastrado"));
        }

        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Deve validar cadastro de usuário quando não encontrar gênero")
    @Order(18)
    void deveValidarCadastroUsuarioGeneroNaoEncontrado(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste123@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(-1L)
                    .idPerfil(perfilAluno.getId())
                    .telefones(new ArrayList<>())
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do gênero não encontrado");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Gênero informado não encontrado"));
        }
    }

    @Test
    @DisplayName("Deve validar cadastro de usuário quando não encontrar perfil")
    @Order(19)
    void deveValidarCadastroUsuarioPerfilNaoEncontrado(){
        try {
            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste123@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial(null)
                    .idGenero(null)
                    .idPerfil(-1L)
                    .telefones(new ArrayList<>())
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do perfil não encontrado");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Perfil informado não encontrado"));
        }
    }

    @Test
    @DisplayName("Deve validar o ddd quando for cadastrar o telefone")
    @Order(20)
    void deveValidarDDDCadastroTelefone() {
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd(null).numero("38508777").idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção da ddd nulo");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("É necessário informar o ddd do telefone do usuário"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "111", "1111"})
    @DisplayName("Deve validar o tamanho do ddd quando for cadastrar o telefone")
    @Order(21)
    void deveValidarDDDTamanhoCadastroTelefone(String ddd){
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd(ddd).numero("38508777").idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tamanho ddd");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o ddd do telefone do usuário até 2 caracteres"));
        }
    }

    @Test
    @DisplayName("Deve validar o número quando for cadastrar o telefone")
    @Order(22)
    void deveValidarNumeroCadastroTelefone(){
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero(null).idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do número nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o número do telefone do usuário"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "11", "111", "1111", "22222", "333333", "7777777", "7777777888"})
    @DisplayName("Deve validar o tamanho do número quando for cadastrar o telefone")
    @Order(23)
    void deveValidarNumeroTamanhoCadastroTelefone(String numero){
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero(numero).idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tamanho número");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o número do telefone do usuário até 255 caracteres"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"3583954A", "abcdefgf", "3583-9545", "abcdefgff"})
    @DisplayName("Deve validar o número válido quando for cadastrar o telefone")
    @Order(24)
    void deveValidarNumeroValidoCadastroTelefone(String numero){
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero(numero).idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do dígito número");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Digite um número com apenas dígitos do telefone para o usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o tipo de telefone quando for cadastrar o telefone")
    @Order(25)
    void deveValidarTipoTelefoneCadastroTelefone(){
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(null).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tipo de telefone nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o tipo de telefone"));
        }
    }

    @Test
    @DisplayName("Deve validar quando tiver numero de telefone e ddd repetidos")
    @Order(26)
    void deveValidarNumeroDDDRepetidos(){
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            telefones.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do número repetido");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Este DDD 11 e número 35839545 já foi inserido, você está repetindo números"));
        }
    }

    @Test
    @DisplayName("Deve validar o número residencial com o tipo de telefone celular")
    @Order(27)
    void deveValidarTipoTelefoneComNumeroDiferenteComTipoTelefoneCelularCadastroTelefone() {
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(tipoTelefoneCelular.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tipo de telefone diferente com número residencial");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Você está inserindo um telefone para o tipo de telefone como celular"));
        }
    }

    @Test
    @DisplayName("Deve validar o número celular com o tipo de telefone residencial")
    @Order(28)
    void deveValidarTipoTelefoneComNumeroDiferenteComTipoTelefoneResidencialCadastroTelefone() {
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero("358395459").idTipoTelefone(tipoTelefoneResidencial.getId()).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tipo de telefone diferente com número celular");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Você está inserindo um celular para o tipo de telefone como residencial"));
        }
    }

    @Test
    @DisplayName("Deve validar o tipo de telefone quando não for encontrado")
    @Order(29)
    void deveValidarTipoTelefoneQuandoNaoForEncontradoCadastroTelefone() {
        try {
            List<TelefoneDTO> telefones = new ArrayList<>();
            telefones.add(TelefoneDTO.builder().ddd("11").numero("358395459").idTipoTelefone(-1L).build());

            UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                    .nome("Teste 123")
                    .email("teste@email.com")
                    .senha("Teste123@")
                    .dataNascimento(LocalDate.of(1990, 12, 20))
                    .cpf("80523545010")
                    .nomeSocial("Teste social")
                    .idGenero(generoMasculino.getId())
                    .idPerfil(perfilAluno.getId())
                    .telefones(telefones)
                    .build();

            usuarioService.inserir(usuarioDTO);
            fail("Deveria ter lançado a exceção do tipo de telefone não encontrado");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Tipo de telefone informado não encontrado"));
        }
    }

    @Test
    @DisplayName("Deve cadastrar usuário sem nome social e sem gênero e sem telefones")
    @Order(30)
    void deveCadastrarUsuarioSemNomeSocialSemGeneroSemTelefones(){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste@email.com")
                .senha("Teste123@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);

        assertNotNull(usuarioResultado.getId());
        assertEquals(usuarioDTO.getNome(), usuarioResultado.getNome());
        assertEquals(usuarioDTO.getEmail(), usuarioResultado.getEmail());
        assertEquals(usuarioDTO.getDataNascimento(), usuarioResultado.getDataNascimento());
        assertEquals(usuarioDTO.getCpf(), usuarioResultado.getCpf());
        assertNull(usuarioResultado.getNomeSocial());
        assertNull(usuarioResultado.getGenero());
        assertEquals(usuarioDTO.getIdPerfil(), usuarioResultado.getPerfil().getId());
        assertTrue(usuarioDTO.getTelefones().isEmpty());

        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Deve buscar usuário sem nome social e sem gênero e sem telefones")
    @Order(31)
    void deveBuscarUsuarioSemNomeSocialSemGeneroSemTelefones(){
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste@email.com")
                .senha("Teste123@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial(null)
                .idGenero(null)
                .idPerfil(perfilAluno.getId())
                .telefones(new ArrayList<>())
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        UsuarioViewDTO usuarioEncontrado = usuarioService.buscarPorId(usuarioResultado.getId());

        assertEquals(usuarioResultado.getId(), usuarioEncontrado.getId());
        assertEquals(usuarioResultado.getNome(), usuarioEncontrado.getNome());
        assertEquals(usuarioResultado.getEmail(), usuarioEncontrado.getEmail());
        assertEquals(usuarioResultado.getDataNascimento(), usuarioEncontrado.getDataNascimento());
        assertEquals(usuarioResultado.getCpf(), usuarioEncontrado.getCpf());
        assertNull(usuarioResultado.getNomeSocial());
        assertNull(usuarioResultado.getGenero());
        assertEquals(usuarioResultado.getPerfil().getId(), usuarioEncontrado.getPerfil().getId());
        assertTrue(usuarioEncontrado.getTelefones().isEmpty());

        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Não deve encontrar usuário por id")
    @Order(32)
    void naoDeveEncontrarUsuarioPorId(){
        assertThrows(RegistroNaoEncontradoException.class, () -> usuarioService.buscarPorId(-1L));
    }

    @Test
    @DisplayName("Deve cadastrar usuário com nome social e com gênero e com telefones")
    @Order(33)
    void deveCadastrarUsuarioComNomeSocialComGeneroComTelefones(){
        List<TelefoneDTO> telefones = new ArrayList<>();
        telefones.add(TelefoneDTO.builder().ddd("11").numero("36228681").idTipoTelefone(tipoTelefoneResidencial.getId()).build());

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste@email.com")
                .senha("Teste123@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial("Teste social")
                .idGenero(generoMasculino.getId())
                .idPerfil(perfilAluno.getId())
                .telefones(telefones)
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);

        assertNotNull(usuarioResultado.getId());
        assertEquals(usuarioDTO.getNome(), usuarioResultado.getNome());
        assertEquals(usuarioDTO.getEmail(), usuarioResultado.getEmail());
        assertEquals(usuarioDTO.getDataNascimento(), usuarioResultado.getDataNascimento());
        assertEquals(usuarioDTO.getCpf(), usuarioResultado.getCpf());
        assertEquals(usuarioDTO.getNomeSocial(), usuarioResultado.getNomeSocial());
        assertEquals(usuarioDTO.getIdGenero(), usuarioResultado.getGenero().getId());
        assertEquals(usuarioDTO.getIdPerfil(), usuarioResultado.getPerfil().getId());
        assertEquals(usuarioDTO.getTelefones().get(0).getDdd(), usuarioDTO.getTelefones().get(0).getDdd());
        assertEquals(usuarioDTO.getTelefones().get(0).getNumero(), usuarioDTO.getTelefones().get(0).getNumero());
        assertEquals(usuarioDTO.getTelefones().get(0).getIdTipoTelefone(), usuarioDTO.getTelefones().get(0).getIdTipoTelefone());

        usuarioService.deletarTelefonesPorIdUsuario(usuarioResultado.getId());
        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    @Test
    @DisplayName("Deve encontrar usuário com nome social e com gênero e com telefones")
    @Order(34)
    void deveEncontrarUsuarioComNomeSocialComGeneroComTelefones(){
        List<TelefoneDTO> telefones = new ArrayList<>();
        telefones.add(TelefoneDTO.builder().ddd("11").numero("36228681").idTipoTelefone(tipoTelefoneResidencial.getId()).build());

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .nome("Teste 123")
                .email("teste@email.com")
                .senha("Teste123@")
                .dataNascimento(LocalDate.of(1990, 12, 20))
                .cpf("80523545010")
                .nomeSocial("Teste social")
                .idGenero(generoMasculino.getId())
                .idPerfil(perfilAluno.getId())
                .telefones(telefones)
                .build();

        UsuarioViewDTO usuarioResultado = usuarioService.inserir(usuarioDTO);
        UsuarioViewDTO usuarioEncontrado = usuarioService.buscarPorId(usuarioResultado.getId());

        assertEquals(usuarioResultado.getId(), usuarioEncontrado.getId());
        assertEquals(usuarioResultado.getNome(), usuarioEncontrado.getNome());
        assertEquals(usuarioResultado.getEmail(), usuarioEncontrado.getEmail());
        assertEquals(usuarioResultado.getDataNascimento(), usuarioEncontrado.getDataNascimento());
        assertEquals(usuarioResultado.getCpf(), usuarioEncontrado.getCpf());
        assertEquals(usuarioResultado.getNomeSocial(), usuarioEncontrado.getNomeSocial());
        assertEquals(usuarioResultado.getGenero().getId(), usuarioEncontrado.getGenero().getId());
        assertEquals(usuarioResultado.getPerfil().getId(), usuarioEncontrado.getPerfil().getId());
        assertNotNull(usuarioEncontrado.getTelefones().get(0).getId());
        assertEquals(usuarioResultado.getTelefones().get(0).getDdd(), usuarioEncontrado.getTelefones().get(0).getDdd());
        assertEquals(usuarioResultado.getTelefones().get(0).getNumero(), usuarioEncontrado.getTelefones().get(0).getNumero());
        assertEquals(usuarioResultado.getTelefones().get(0).getTipoTelefone().getId(), usuarioEncontrado.getTelefones().get(0).getTipoTelefone().getId());
        assertEquals("RESIDENCIAL", usuarioEncontrado.getTelefones().get(0).getTipoTelefone().getCodigo());
        assertEquals("Residencial", usuarioEncontrado.getTelefones().get(0).getTipoTelefone().getDescricao());

        usuarioService.deletarTelefonesPorIdUsuario(usuarioResultado.getId());
        usuarioService.deletarPorId(usuarioResultado.getId());
    }

    private String gerarStringGrande(){
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam a ultricies elit. Etiam iaculis sem in lorem aliquet fermentum. " +
                "In et sodales diam, eu pharetra magna. Donec vel lorem tempor, facilisis augue in, aliquam tellus. " +
                "Sed a ligula volutpat, aliquam metus id, ullamcorper velit. Nam quam elit, feugiat quis libero et, venenatis vulputate metus. " +
                "Aliquam tincidunt mauris et felis vulputate, eget faucibus nunc ultricies. Donec imperdiet arcu lacus. " +
                "Phasellus viverra nulla eu consectetur blandit. Curabitur in ipsum sodales, pretium enim in, iaculis sapien. " +
                "Integer lacinia mi at ligula tincidunt suscipit. " +
                "Pellentesque malesuada, diam ut posuere sagittis, tortor nisl facilisis mi, et facilisis quam tellus sit amet sapien. " +
                "Curabitur varius ante a mi ornare facilisis. Sed luctus scelerisque lorem id lobortis. Maecenas ornare lacus vitae eleifend porta. " +
                "Etiam euismod pharetra turpis, sit amet volutpat magna egestas sit amet.";
    }
}
