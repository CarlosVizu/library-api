package com.interview.libraryapi.unit.mock;

import com.interview.libraryapi.data.dto.v1.UsuarioDTO;
import com.interview.libraryapi.model.Usuario;
import org.hibernate.event.internal.DefaultPersistOnFlushEventListener;

import java.util.Date;

public class UsuarioMock {

    public static Usuario usuarioValido(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("email_teste@dominio.com");
        usuario.setDataCadastro(new Date());
        usuario.setNome("Teste Unitario");
        usuario.setTelefone("43999999999");

        return usuario;
    }

    public static UsuarioDTO usuarioDTOValido(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setEmail("email_teste@dominio.com");
        usuarioDTO.setDataCadastro(new Date());
        usuarioDTO.setNome("Teste Unitario");
        usuarioDTO.setTelefone("43999999999");

        return usuarioDTO;
    }
}
