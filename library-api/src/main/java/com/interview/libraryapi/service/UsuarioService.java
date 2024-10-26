package com.interview.libraryapi.service;

import com.interview.libraryapi.data.dto.v1.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    public void salvarUsuario(UsuarioDTO usuarioDTO);

    public List<UsuarioDTO> procurarUsuarioPorNome(String nome);

    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO);

    public void deletarUsuario(UsuarioDTO usuarioDTO);
}
