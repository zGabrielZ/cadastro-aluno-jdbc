package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.DaoFactory;
import br.com.gabrielferreira.modelo.TipoTelefone;
import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.TelefoneDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.utils.BancoDeDadosAmbienteEnum.TESTE;
import static org.junit.jupiter.api.Assertions.*;

class TelefoneServiceTest {

    private TelefoneService telefoneService;

    private TipoTelefone tipoTelefoneResidencial;

    private TipoTelefone tipoTelefoneCelular;


    @BeforeEach
    public void criarInstancias(){
        TipoTelefoneService tipoTelefoneService = new TipoTelefoneService(DaoFactory.criarTipoTelefoneDao(TESTE));
        telefoneService = new TelefoneService(DaoFactory.criarTelefoneDao(TESTE), tipoTelefoneService);

        tipoTelefoneResidencial = tipoTelefoneService.buscarTipoTelefonePorCodigo("RESIDENCIAL");
        tipoTelefoneCelular = tipoTelefoneService.buscarTipoTelefonePorCodigo("CELULAR");
    }

    @Test
    @DisplayName("Deve validar o ddd quando for cadastrar o telefone")
    void deveValidarDDDCadastroTelefone(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd(null).numero("38508777").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção da ddd nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o ddd do telefone do usuário"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "111", "1111"})
    @DisplayName("Deve validar o tamanho do ddd quando for cadastrar o telefone")
    void deveValidarDDDTamanhoCadastroTelefone(String ddd){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd(ddd).numero("38508777").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do tamanho ddd");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o ddd do telefone do usuário até 2 caracteres"));
        }
    }

    @Test
    @DisplayName("Deve validar o número quando for cadastrar o telefone")
    void deveValidarNumeroCadastroTelefone(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero(null).idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do número nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o número do telefone do usuário"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "11", "111", "1111", "22222", "333333", "7777777", "7777777888"})
    @DisplayName("Deve validar o tamanho do número quando for cadastrar o telefone")
    void deveValidarNumeroTamanhoCadastroTelefone(String numero){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero(numero).idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do tamanho número");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o número do telefone do usuário até 255 caracteres"));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"3583954A", "abcdefgf", "3583-9545", "abcdefgff"})
    @DisplayName("Deve validar o número válido quando for cadastrar o telefone")
    void deveValidarNumeroValidoCadastroTelefone(String numero){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero(numero).idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do dígito número");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Digite um número com apenas dígitos do telefone para o usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o tipo de telefone quando for cadastrar o telefone")
    void deveValidarTipoTelefoneCadastroTelefone(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(null).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do tipo de telefone nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o tipo de telefone"));
        }
    }

    @Test
    @DisplayName("Deve validar o usuário quando for cadastrar o telefone")
    void deveValidarUsuarioCadastroTelefone(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(null).build());
            Usuario usuario = Usuario.builder().id(null).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do usuario com id nulo");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("É necessário informar o usuário"));
        }
    }

    @Test
    @DisplayName("Deve validar o número residencial com o tipo de telefone celular")
    void deveValidarTipoTelefoneComNumeroDiferenteComTipoTelefoneCelularCadastroTelefone(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(tipoTelefoneCelular.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do tipo de telefone diferente com número residencial");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Você está inserindo um telefone para o tipo de telefone como celular"));
        }
    }

    @Test
    @DisplayName("Deve validar o número celular com o tipo de telefone residencial")
    void deveValidarTipoTelefoneComNumeroDiferenteComTipoTelefoneResidencialCadastroTelefone(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("358395459").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do tipo de telefone diferente com número celular");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Você está inserindo um celular para o tipo de telefone como residencial"));
        }
    }

    @Test
    @DisplayName("Deve validar quando não encontrar usuário")
    void deveValidarQuandoNaoEncontrarUsuario(){
        try {
            List<TelefoneDTO> telefoneDTOS = new ArrayList<>();
            telefoneDTOS.add(TelefoneDTO.builder().ddd("11").numero("35839545").idTipoTelefone(tipoTelefoneResidencial.getId()).build());
            Usuario usuario = Usuario.builder().id(-1L).build();
            telefoneService.inserir(usuario, telefoneDTOS);
            fail("Deveria ter lançado a exceção do usuário inexistente");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Usuário informado não encontrado"));
        }
    }
}
