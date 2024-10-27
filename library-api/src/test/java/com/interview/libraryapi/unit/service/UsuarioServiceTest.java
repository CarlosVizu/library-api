package com.interview.libraryapi.unit.service;

import com.interview.libraryapi.data.dto.v1.UsuarioDTO;
import com.interview.libraryapi.exceptions.ParameterUpdateInvalid;
import com.interview.libraryapi.exceptions.ResourceNotFoundException;
import com.interview.libraryapi.model.Usuario;
import com.interview.libraryapi.repositories.UsuarioRepository;
import com.interview.libraryapi.service.impl.UsuarioServiceImpl;
import com.interview.libraryapi.unit.mock.UsuarioMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;


    @Test
    public void UsuarioService_salvarUsuario_RetornaDto() {
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        Usuario usuario = new Usuario(usuarioDTO);

        when(usuarioRepository.save(Mockito.any(Usuario.class)))
                .thenReturn(usuario);

        UsuarioDTO usuarioSalvo = usuarioService.salvarUsuario(usuarioDTO);

        Assertions.assertThat(usuarioSalvo).isNotNull();
    }

    @Test
    public void UsuarioService_procurarUsuarioPorNome_RetornaUsuarioExistente() {
        Usuario usuario = UsuarioMock.usuarioValido();
        when(usuarioRepository.procurarPorNome("teste unitario".toLowerCase())).thenReturn(Arrays.asList(usuario));

        List<UsuarioDTO> resultado = usuarioService.procurarUsuarioPorNome("teste unitario");
        assertEquals("teste unitario".toLowerCase(), resultado.get(0).getNome().toLowerCase());
    }

    @Test
    public void UsuarioService_procurarUsuarioPorNome_RetornaUsuarioInexistente() {
        String nome = "inexistente";
        when(usuarioRepository.procurarPorNome(nome))
                .thenReturn(new ArrayList<>());

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> usuarioService.procurarUsuarioPorNome(nome));
        assertEquals("Nenhum usuário foi encontrado com este nome.", exception.getMessage());
    }

    @Test
    public void UsuarioService_atualizarUsuario_RetornaUsuarioAtualizado() {
        Long usuarioId = 1L;
        Usuario usuario = UsuarioMock.usuarioValido();
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        usuarioDTO.setNome("teste atualizado");
        usuarioDTO.setEmail("teste_atualizado@dominio.com");
        usuarioDTO.setTelefone("32996642221");

        when(usuarioRepository.findById(usuarioDTO.getId())).thenReturn(Optional.of(usuario));

        UsuarioDTO resultado = usuarioService.atualizarUsuario(usuarioDTO);
        assertEquals(usuarioDTO, resultado);
    }

    @Test
    public void UsuarioService_atualizarUsuario_RetornaUsuarioInexistente() {
        Long usuarioId = 2L;
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        usuarioDTO.setId(usuarioId);
        when(usuarioRepository.findById(usuarioDTO.getId())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> usuarioService.atualizarUsuario(usuarioDTO));
        assertEquals("Nenhum usuário encontrado.", exception.getMessage());
    }

    @Test
    public void UsuarioService_atualizarUsuario_RetornaEmailInvalido() {
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        Usuario usuario = UsuarioMock.usuarioValido();
        when(usuarioRepository.findById(usuarioDTO.getId())).thenReturn(Optional.of(usuario));

        usuarioDTO.setEmail("formato_email_invalido@com");

        Throwable exception = assertThrows(ParameterUpdateInvalid.class, () -> usuarioService.atualizarUsuario(usuarioDTO));
        assertEquals("Email informado é invalido: insira um endereço de email válido.", exception.getMessage());
    }

    @Test
    public void UsuarioService_atualizarUsuario_RetornaDataCadastroInvalida() {
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        Usuario usuario = UsuarioMock.usuarioValido();
        when(usuarioRepository.findById(usuarioDTO.getId())).thenReturn(Optional.of(usuario));

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_YEAR, 1);
        Date dataInvalida = calendario.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataInvalidaFormatada = null;
        try {
            dataInvalidaFormatada = dateFormat.parse(dateFormat.format(dataInvalida));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        usuarioDTO.setDataCadastro(dataInvalidaFormatada);

        Throwable exception = assertThrows(ParameterUpdateInvalid.class, () -> usuarioService.atualizarUsuario(usuarioDTO));
        assertEquals("Data de cadastro inválida: Informe uma data anterior ou igual a atual.", exception.getMessage());
    }

    @Test
    public void UsuarioService_deletarUsuario_DeletaUsuarioRetornaVazio() {
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        Usuario usuario = UsuarioMock.usuarioValido();
        when(usuarioRepository.findById(usuarioDTO.getId())).thenReturn(Optional.of(usuario));

        doNothing().when(usuarioRepository).delete(usuario);
        assertAll(() -> usuarioService.deletarUsuario(usuarioDTO.getId()));
    }

    @Test
    public void UsuarioService_deletarUsuario_RetornaUsuarioInexistente() {
        Long usuarioId = 2L;
        UsuarioDTO usuarioDTO = UsuarioMock.usuarioDTOValido();
        usuarioDTO.setId(usuarioId);
        when(usuarioRepository.findById(usuarioDTO.getId())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> usuarioService.deletarUsuario(usuarioDTO.getId()));
        assertEquals("Nenhum usuário encontrado.", exception.getMessage());

    }

}
