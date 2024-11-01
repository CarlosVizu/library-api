package com.interview.libraryapi.service;

import com.interview.libraryapi.data.dto.v1.UsuarioDTO;
import com.interview.libraryapi.model.Usuario;

import java.util.List;

public interface UsuarioService {

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO);

    public List<UsuarioDTO> procurarUsuarioPorNome(String nome);

    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO);

    public void deletarUsuario(Long id);

    public Usuario validarUsuarioExiste(Long id);
}
