package br.com.gabrielferreira.conexao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ConexaoBDTest {

    @Test
    @DisplayName("Deve não conectar no banco de dados quando informar ambiente incorreto")
    void deveNaoConectarComBancoDeDadosAmbienteIncorreto(){
        try {
            new ConexaoBD("AMBIENTE_INCORRETO");
            fail("Deveria ter lançado a exceção do ambiente incorreto");
        } catch (Exception e){
            assertTrue(e.getMessage().contains("Ocorreu um erro ao conectar no banco de dados"));
        }
    }
}
