package com.interview.libraryapi.unit.service;

import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.data.dto.v1.UsuarioDTO;
import com.interview.libraryapi.exceptions.ResourceNotFoundException;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.model.Usuario;
import com.interview.libraryapi.repositories.LivroRepository;
import com.interview.libraryapi.service.impl.LivroServiceImpl;
import com.interview.libraryapi.unit.mock.LivroMock;
import com.interview.libraryapi.unit.mock.UsuarioMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroServiceImpl livroService;

    @Test
    public void LivroService_salvarLivro_RetornaDto() {
        LivroDTO livroDTO = LivroMock.livroDTOValido();
        Livro livro = new Livro(livroDTO);

        when(livroRepository.save(Mockito.any(Livro.class)))
                .thenReturn(livro);

        LivroDTO livroSalvo = livroService.salvarLivro(livroDTO);

        Assertions.assertThat(livroSalvo).isNotNull();
    }

    @Test
    public void LivroService_procurarLivroPorTitulo_RetornaLivroExistente() {
        Livro livro = LivroMock.livroValido();
        when(livroRepository.procurarLivroPorTitulo("Titulo teste".toLowerCase())).thenReturn(Arrays.asList(livro));

        List<LivroDTO> resultado = livroService.procurarLivroPorTitulo("Titulo teste");
        assertEquals("Titulo teste".toLowerCase(), resultado.get(0).getTitulo().toLowerCase());
    }

    @Test
    public void LivroService_procurarLivroPorTitulo_RetornaLivroInexistente() {
        String titulo = "inexistente";
        when(livroRepository.procurarLivroPorTitulo(titulo))
                .thenReturn(new ArrayList<>());

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> livroService.procurarLivroPorTitulo(titulo));
        assertEquals("Nenhum livro foi encontrado com este titulo.", exception.getMessage());
    }

    @Test
    public void LivroService_atualizarLivro_RetornaLivroAtualizado() {
        Long livroId = 1L;
        Livro livro = LivroMock.livroValido();
        LivroDTO livroDTO = LivroMock.livroDTOValido();
        livroDTO.setTitulo("Teste titulo2");
        livroDTO.setAutor("Autor teste2");
        livroDTO.setCategoria("Categoria2");
        livroDTO.setDataPublicacao(new Date());
        livroDTO.setIsbn("9329329322222");

        when(livroRepository.findById(livroDTO.getId())).thenReturn(Optional.of(livro));

        LivroDTO resultado = livroService.atualizarLivro(livroDTO);
        assertEquals(livroDTO, resultado);
    }

    @Test
    public void LivroService_atualizarLivro_RetornaLivroInexistente() {
        Long livroId = 2L;
        LivroDTO livroDTO = LivroMock.livroDTOValido();
        livroDTO.setId(livroId);
        when(livroRepository.findById(livroDTO.getId())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResourceNotFoundException.class,
                () -> livroService.atualizarLivro(livroDTO));
        assertEquals("Nenhum livro encontrado.", exception.getMessage());
    }

    @Test
    public void LivroService_deletarLivro_DeletaLivroRetornaVazio() {
        LivroDTO livroDTO = LivroMock.livroDTOValido();
        Livro livro = LivroMock.livroValido();
        when(livroRepository.findById(livroDTO.getId())).thenReturn(Optional.of(livro));

        doNothing().when(livroRepository).delete(livro);
        assertAll(() -> livroService.deletarLivro(livroDTO.getId()));
    }

    @Test
    public void LivroService_deletarLivro_RetornaLivroInexistente() {
        Long livroId = 2L;
        LivroDTO livroDTO = LivroMock.livroDTOValido();
        livroDTO.setId(livroId);
        when(livroRepository.findById(livroDTO.getId())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResourceNotFoundException.class,
                () -> livroService.deletarLivro(livroDTO.getId()));
        assertEquals("Nenhum livro encontrado.", exception.getMessage());

    }
}
