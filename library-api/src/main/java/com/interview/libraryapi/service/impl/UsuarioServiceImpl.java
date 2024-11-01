package com.interview.libraryapi.service.impl;

import com.interview.libraryapi.data.dto.v1.UsuarioDTO;
import com.interview.libraryapi.exceptions.ParameterUpdateInvalid;
import com.interview.libraryapi.exceptions.RequiredObjectIsNullException;
import com.interview.libraryapi.exceptions.ResourceNotFoundException;
import com.interview.libraryapi.model.Usuario;
import com.interview.libraryapi.repositories.UsuarioRepository;
import com.interview.libraryapi.service.UsuarioService;
import com.interview.libraryapi.util.FormatValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository repository;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        if (!FormatValidate.validarEmail(usuarioDTO.getEmail())) {
            throw new ParameterUpdateInvalid("Email informado é invalido: insira um endereço de email válido.");
        }
        Usuario usuario = repository.save(new Usuario(usuarioDTO));
        return new UsuarioDTO(usuario);
    }

    public List<UsuarioDTO> procurarUsuarioPorNome(String nome) {

        List<Usuario> user = repository.procurarPorNome(nome.toLowerCase());
        if(user.isEmpty() || nome.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário foi encontrado com este nome.");
        }
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        for(Usuario usuario : user) {
            usuarioDTOList.add(new UsuarioDTO(usuario));
        }

        return usuarioDTOList;
    }

    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = validarUsuarioExiste(usuarioDTO.getId());

        if(!usuario.getEmail().equals(usuarioDTO.getEmail())) {
            if(!FormatValidate.validarEmail(usuarioDTO.getEmail()))
                throw new ParameterUpdateInvalid("Email informado é invalido: insira um endereço de email válido.");
        }

        if(!usuario.getDataCadastro().equals(usuarioDTO.getDataCadastro())) {
            if(!FormatValidate.validarDataAnteriorHoje(usuarioDTO.getDataCadastro()))
                throw new ParameterUpdateInvalid("Data de cadastro inválida: Informe uma data anterior ou igual a atual.");
        }

        repository.save(new Usuario(usuarioDTO));

        return usuarioDTO;
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = validarUsuarioExiste(id);
        repository.delete(usuario);
    }

    public Usuario validarUsuarioExiste(Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if(!usuario.isPresent()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado.");
        }
        return usuario.get();
    }
}
