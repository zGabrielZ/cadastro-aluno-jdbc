package br.com.gabrielferreira.service;

import br.com.gabrielferreira.dao.UsuarioDAO;
import br.com.gabrielferreira.dto.TelefoneDTO;
import br.com.gabrielferreira.dto.UsuarioDTO;
import br.com.gabrielferreira.dto.create.UsuarioCreateDTO;
import br.com.gabrielferreira.dto.update.UsuarioUpdateDTO;
import br.com.gabrielferreira.exception.ErroException;
import br.com.gabrielferreira.exception.RegistroNaoEncontradoException;
import br.com.gabrielferreira.exception.TelefoneException;
import br.com.gabrielferreira.exception.UsuarioException;
import br.com.gabrielferreira.model.Genero;
import br.com.gabrielferreira.model.Telefone;
import br.com.gabrielferreira.model.Usuario;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.dto.factory.TelefoneDTOFactory.toTelefonesDTO;
import static br.com.gabrielferreira.dto.factory.UsuarioDTOFactory.toUsuarioDTO;
import static br.com.gabrielferreira.model.factory.TelefoneFactory.toTelefones;
import static br.com.gabrielferreira.model.factory.UsuarioFactory.toUsuario;
import static br.com.gabrielferreira.model.factory.UsuarioFactory.toUsuarioAtualizar;
import static br.com.gabrielferreira.utils.LogUtils.gerarLogWarn;
import static br.com.gabrielferreira.utils.StringCriptografarUtils.criptarCampo;
import static br.com.gabrielferreira.utils.ValidarTelefoneUtils.validarCamposNaoInformadosCadastroTelefone;
import static br.com.gabrielferreira.utils.ValidarTelefoneUtils.validarNumeroDDDRepetido;
import static br.com.gabrielferreira.utils.ValidarUsuarioUtils.validarCamposNaoInformadosAtualizarUsuario;
import static br.com.gabrielferreira.utils.ValidarUsuarioUtils.validarCamposNaoInformadosCadastroUsuario;

@AllArgsConstructor
public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    private TelefoneService telefoneService;

    private GeneroService generoService;

    public UsuarioDTO inserir(UsuarioCreateDTO usuarioCreateDTO){
        List<TelefoneDTO> telefoneViewDTOList = new ArrayList<>();

        Usuario usuario = toUsuario(usuarioCreateDTO);
        validarCamposNaoInformadosCadastroUsuario(usuario);
        usuario.setSenha(criptarCampo(usuario.getSenha()));

        if(usuarioCreateDTO.telefones().isEmpty()){
            inserirUsuario(usuario);
        } else {
            List<Telefone> telefones = toTelefones(usuarioCreateDTO.telefones());

            List<String> numeroDDD = new ArrayList<>();
            for (Telefone telefone : telefones) {
                validarCamposNaoInformadosCadastroTelefone(telefone);
                telefoneService.validarTipoTelefoneComNumero(telefone.getNumero(), telefone.getTipoTelefone());
                numeroDDD.add(telefone.getDdd().concat(telefone.getNumero()));
            }

            validarNumeroDDDRepetido(numeroDDD);

            inserirUsuarioComTelefones(usuario, telefones);
            telefoneViewDTOList = toTelefonesDTO(telefones);
        }

        return toUsuarioDTO(usuario, telefoneViewDTOList);
    }

    public UsuarioDTO buscarPorId(Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        List<TelefoneDTO> telefones = telefoneService.buscarTelefonesPorIdUsuario(usuario.getId());
        return toUsuarioDTO(usuario, telefones);
    }

    public UsuarioDTO atualizar(UsuarioUpdateDTO usuarioUpdateDTO, Long id){
        Usuario usuario = buscarUsuarioPorId(id);
        Genero generoEncontrado = generoService.buscarGeneroPorId(usuarioUpdateDTO.idGenero());
        List<TelefoneDTO> telefones = telefoneService.buscarTelefonesPorIdUsuario(usuario.getId());

        try {
            toUsuarioAtualizar(usuario, generoEncontrado, usuarioUpdateDTO);
            validarCamposNaoInformadosAtualizarUsuario(usuario);
            usuarioDAO.atualizar(usuario, usuario.getId());
        } catch (Exception e){
            gerarLogWarn("Erro ao atualizar o usuário, {}", e);
            throw new ErroException("Erro ao atualizar o usuário, tente mais tarde.");
        }

        return toUsuarioDTO(usuario, telefones);
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
