package br.com.gabrielferreira.aluno.conexao;
import br.com.gabrielferreira.aluno.conexao.ConexaoBD;
import br.com.gabrielferreira.aluno.conexao.config.ConfigBandoDeDadosDevImpl;
import br.com.gabrielferreira.aluno.conexao.config.ConfigBandoDeDadosTestImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConexaoBDTest {

    @Test
    @DisplayName("Deve não conectar no banco de dados quando informar ambiente incorreto")
    @Order(1)
    void deveNaoConectarComBancoDeDadosAmbienteIncorreto(){
        try {
            new ConexaoBD(null);
            fail("Deveria ter lançado a exceção do ambiente incorreto");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Ocorreu um erro ao conectar no banco de dados"));
        }
    }

    @Test
    @DisplayName("Deve conectar com o ambiente teste")
    @Order(2)
    void deveConectarComAmbienteTeste(){
        ConexaoBD conexaoBD = new ConexaoBD(new ConfigBandoDeDadosTestImpl());

        assertNotNull(conexaoBD.getConnection());
    }

    @Test
    @DisplayName("Deve conectar com o ambiente teste")
    @Order(3)
    void deveConectarComAmbienteDev(){
        ConexaoBD conexaoBD = new ConexaoBD(new ConfigBandoDeDadosDevImpl());

        assertNotNull(conexaoBD.getConnection());
    }
}
