package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.exceptions.RegraDeNegocioException;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Perfil;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.dto.TelefoneDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;

    private Genero generoMasculino;

    private Perfil perfilAluno;

    private TipoTelefone tipoTelefoneResidencial;

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
    }

    @Test
    @DisplayName("Deve validar o nome quando for cadastrar o usuário")
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
    @DisplayName("Deve cadastrar usuário sem nome social e sem gênero e sem telefones")
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
    @DisplayName("Deve cadastrar usuário com nome social e com gênero e com telefones")
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
