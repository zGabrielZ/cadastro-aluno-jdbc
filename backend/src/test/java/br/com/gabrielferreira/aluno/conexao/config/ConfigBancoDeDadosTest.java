package br.com.gabrielferreira.aluno.conexao.config;

import br.com.gabrielferreira.aluno.conexao.config.modelo.InformacaoBancoDeDados;
import br.com.gabrielferreira.aluno.exception.ErroException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfigBancoDeDadosTest {

    private ConfigBancoDeDados configBancoDeDados;

    @BeforeEach
    void criarInstancias() {
        configBancoDeDados = new ConfigBancoDeDados();
    }

    @Test
    @DisplayName("Deve recuperar ambiente de teste")
    @Order(1)
    void deveRecuperarAmbienteDeTest() {
        System.setProperty("AMBIENTE", "TEST");
        InformacaoBancoDeDados informacaoBancoDeDados = configBancoDeDados.getInformacaoBancoDeDados();

        assertNotNull(informacaoBancoDeDados);
    }

    @Test
    @DisplayName("Deve recuperar ambiente de dev")
    @Order(2)
    void deveRecuperarAmbienteDeDev() {
        System.setProperty("AMBIENTE", "DEV");
        InformacaoBancoDeDados informacaoBancoDeDados = configBancoDeDados.getInformacaoBancoDeDados();

        assertNotNull(informacaoBancoDeDados);
    }

    @Test
    @DisplayName("Não deve recuperar ambiente quando não informar o ambiente via property")
    @Order(3)
    void naoDeveRecuperarAmbienteQuandoNaoInformar() {
        System.setProperty("AMBIENTE", "ERRO");
        assertThrows(ErroException.class, () -> configBancoDeDados.getInformacaoBancoDeDados());
    }
}