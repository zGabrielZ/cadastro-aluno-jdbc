package br.com.gabrielferreira.aluno.dao.factory;

import br.com.gabrielferreira.aluno.model.Genero;
import br.com.gabrielferreira.aluno.model.Perfil;
import br.com.gabrielferreira.aluno.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioDAOFactory {

    private UsuarioDAOFactory() {}

    public static Usuario toFromModel(ResultSet resultSet) throws SQLException {
        LocalDate dataNascimento = toLocalDateDataNascimento(resultSet.getObject("DATA_NASCIMENTO", LocalDate.class));
        Genero genero = toGenero(resultSet);
        Perfil perfil = toPerfil(resultSet);

        return Usuario.builder()
                .id(resultSet.getLong("ID"))
                .nome(resultSet.getString("NOME"))
                .email(resultSet.getString("EMAIL"))
                .senha(resultSet.getString("EMAIL"))
                .dataNascimento(dataNascimento)
                .cpf(resultSet.getString("CPF"))
                .nomeSocial(resultSet.getString("NOME_SOCIAL"))
                .genero(genero)
                .perfil(perfil)
                .build();
    }

    private static LocalDate toLocalDateDataNascimento(Object dataNascimento){
        if(dataNascimento instanceof LocalDate dataConvertida){
            return dataConvertida;
        }
        return null;
    }

    private static Genero toGenero(ResultSet resultSet) throws SQLException{
        long idGenero = resultSet.getLong("ID_GENERO");
        if(idGenero != 0){
            return Genero.builder()
                    .id(idGenero)
                    .descricao(resultSet.getString("DESCRICAO_GENERO"))
                    .codigo(resultSet.getString("CODIGO_GENERO"))
                    .build();
        }
        return null;
    }

    private static Perfil toPerfil(ResultSet resultSet) throws SQLException{
        long idPerfil = resultSet.getLong("ID_PERFIL");
        if(idPerfil != 0){
            return Perfil.builder()
                    .id(idPerfil)
                    .descricao(resultSet.getString("DESCRICAO_PERFIL"))
                    .codigo(resultSet.getString("CODIGO_PERFIL"))
                    .build();
        }
        return null;
    }
}
