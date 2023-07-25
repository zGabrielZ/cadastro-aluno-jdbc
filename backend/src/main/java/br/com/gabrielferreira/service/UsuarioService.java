package br.com.gabrielferreira.service;
import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.exceptions.*;
import br.com.gabrielferreira.modelo.Genero;
import br.com.gabrielferreira.modelo.Telefone;
import br.com.gabrielferreira.modelo.Usuario;
import br.com.gabrielferreira.modelo.dto.TelefoneViewDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioAtualizarDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioDTO;
import br.com.gabrielferreira.modelo.dto.UsuarioViewDTO;
import lombok.AllArgsConstructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.modelo.factory.UsuarioFactory.*;
import static br.com.gabrielferreira.modelo.dto.factory.UsuarioDTOFactory.*;
import static br.com.gabrielferreira.validate.ValidarTelefone.*;
import static br.com.gabrielferreira.validate.ValidarUsuario.*;
import static br.com.gabrielferreira.utils.StringCriptografar.*;
import static br.com.gabrielferreira.modelo.factory.TelefoneFactory.*;
import static br.com.gabrielferreira.modelo.dto.factory.TelefoneDTOFactory.*;
import static br.com.gabrielferreira.utils.LogUtils.*;

@AllArgsConstructor
public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    private TelefoneService telefoneService;

    private GeneroService generoService;

    public UsuarioViewDTO inserir(UsuarioDTO usuarioDTO){
        List<TelefoneViewDTO> telefoneViewDTOList = new ArrayList<>();

        Usuario usuario = toUsuario(usuarioDTO);
        validarCamposNaoInformadosCadastroUsuario(usuario);
        usuario.setSenha(criptarCampo(usuario.getSenha()));

        if(usuarioDTO.getTelefones().isEmpty()){
            inserirUsuario(usuario);
        } else {
            List<Telefone> telefones = toTelefones(usuarioDTO.getTelefones());

            List<String> numeroDDD = new ArrayList<>();
            for (Telefone telefone : telefones) {
                validarCamposNaoInformadosCadastroTelefone(telefone);
                telefoneService.validarTipoTelefoneComNumero(telefone.getNumero(), telefone.getTipoTelefone());
                numeroDDD.add(telefone.getDdd().concat(telefone.getNumero()));
            }

            validarNumeroDDDRepetido(numeroDDD);

            inserirUsuarioComTelefones(usuario, telefones);
            telefoneViewDTOList = toTelefonesViewDTO(telefones);
        }

        UsuarioViewDTO usuarioViewDTO = toUsuarioViewDTO(usuario);
        usuarioViewDTO.setTelefones(telefoneViewDTOList);
        return usuarioViewDTO;
    }

    public UsuarioViewDTO buscarPorId(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        List<TelefoneViewDTO> telefones = telefoneService.buscarTelefonesPorIdUsuario(usuario.getId());

        UsuarioViewDTO usuarioViewDTO = toUsuarioViewDTO(usuario);
        usuarioViewDTO.setTelefones(telefones);
        return usuarioViewDTO;
    }

    public UsuarioViewDTO atualizar(UsuarioAtualizarDTO usuarioAtualizarDTO, Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        Genero generoEncontrado = generoService.buscarGeneroPorId(usuarioAtualizarDTO.getIdGenero());

        try {
            toUsuarioAtualizar(usuario, generoEncontrado, usuarioAtualizarDTO);
            validarCamposNaoInformadosAtualizarUsuario(usuario);
            usuarioDAO.atualizar(usuario, usuario.getId());
        } catch (Exception e){
            gerarLogWarn("Erro ao atualizar o usuário, {}", e);
            throw new ErroException("Erro ao atualizar o usuário, tente mais tarde.");
        }

        return toUsuarioViewDTO(usuario);
    }

    public void deletarPorId(Long id){
        Usuario usuario;
        try {
            usuario = buscarUsuarioPorId(id);
            usuarioDAO.deletarPorId(usuario.getId());
        } catch (Exception e){
            gerarLogWarn("Erro ao deletar o usuário, {}", e);
            if(e instanceof RegistroNaoEncontradoException registroNaoEncontradoException){
                throw new ErroException(registroNaoEncontradoException.getMessage());
            }
            throw new ErroException("Erro ao deletar o usuário, tente mais tarde.");
        }
    }

    public void deletarTelefonesPorIdUsuario(Long id){
        Usuario usuario;
        try {
            usuario = buscarUsuarioPorId(id);
            usuarioDAO.deletarTelefonesPorIdUsuario(usuario.getId());
        } catch (Exception e){
            gerarLogWarn("Erro ao deletar o telefones do usuário, {}", e);
            if(e instanceof RegistroNaoEncontradoException registroNaoEncontradoException){
                throw new ErroException(registroNaoEncontradoException.getMessage());
            }
            throw new ErroException("Erro ao deletar os telefones do usuário, tente mais tarde.");
        }
    }

    public void deletarTudo(){
        try {
            usuarioDAO.deleteTudo();
        } catch (Exception e){
            gerarLogWarn("Erro ao deletar tudo no usuário, {}", e);
            throw new ErroException("Erro ao deletar tudo no usuário, tente mais tarde.");
        }
    }

    private Usuario buscarUsuarioPorId(Long id){
        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            if(usuario == null){
                throw new ErroException("Usuário não encontrado");
            }
            return usuario;
        } catch (Exception e){
            gerarLogWarn("Erro ao buscar o usuário, {}", e);
            throw new RegistroNaoEncontradoException(e.getMessage());
        }
    }

    private void inserirUsuario(Usuario usuario){
        try {
            usuarioDAO.inserir(usuario);
        } catch (Exception e){
            gerarLogWarn("Erro ao inserir o usuário, {}", e);
            incluirException(e);
        }
    }

    private void inserirUsuarioComTelefones(Usuario usuario, List<Telefone> telefones){
        try {
            usuarioDAO.inserirUsuarioComTelefones(usuario, telefones);
        } catch (Exception e){
            gerarLogWarn("Erro ao inserir o usuário, {}", e);
            incluirException(e);
        }
    }

    private void incluirException(Exception e){
        if(e instanceof SQLException sqlException){
            if(sqlException.getMessage().contains("ck_email_unique")){
                throw new UsuarioException("Este e-mail informado já foi cadastrado");
            } else if(sqlException.getMessage().contains("ck_cpf_unique")){
                throw new UsuarioException("Este CPF informado já foi cadastrado");
            } else if(sqlException.getMessage().contains("usuario_id_genero_fkey")){
                throw new UsuarioException("Gênero informado não encontrado");
            } else if(sqlException.getMessage().contains("usuario_id_perfil_fkey")){
                throw new UsuarioException("Perfil informado não encontrado");
            } else if(sqlException.getMessage().contains("telefone_id_tipo_telefone_fkey")){
                throw new TelefoneException("Tipo de telefone informado não encontrado");
            }
        }

        throw new ErroException("Erro ao salvar o usuário, tente mais tarde.");
    }
}
