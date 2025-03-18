package br.com.gabrielferreira.aluno.service.factory;

import br.com.gabrielferreira.aluno.dao.*;
import br.com.gabrielferreira.aluno.service.*;
import br.com.gabrielferreira.aluno.service.impl.*;

public class ServiceFactory {

    private ServiceFactory() {}

    public static GeneroService criarGeneroService(GeneroDAO generoDAO) {
        return new GeneroServiceImpl(generoDAO);
    }

    public static PerfilService criarPerfilService(PerfilDAO perfilDAO) {
        return new PerfilServiceImpl(perfilDAO);
    }

    public static TipoTelefoneService criarTipoTelefoneService(TipoTelefoneDAO tipoTelefoneDAO) {
        return new TipoTelefoneServiceImpl(tipoTelefoneDAO);
    }

    public static TelefoneService criarTelefoneService(TelefoneDAO telefoneDAO, TipoTelefoneDAO tipoTelefoneDAO) {
        return new TelefoneServiceImpl(telefoneDAO, criarTipoTelefoneService(tipoTelefoneDAO));
    }

    public static UsuarioService criarUsuarioService(UsuarioDAO usuarioDAO, TelefoneDAO telefoneDAO, TipoTelefoneDAO tipoTelefoneDAO,
                                                     GeneroDAO generoDAO) {
        return new UsuarioServiceImpl(usuarioDAO, criarTelefoneService(telefoneDAO, tipoTelefoneDAO),
                criarGeneroService(generoDAO));
    }
}
