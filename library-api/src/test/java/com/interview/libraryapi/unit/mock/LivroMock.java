package com.interview.libraryapi.unit.mock;

import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.model.Livro;

import java.util.Date;

public class LivroMock {

    public static Livro livroValido() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Titulo teste");
        livro.setAutor("Autor Teste");
        livro.setIsbn("1235647543251");
        livro.setCategoria("Terror");
        livro.setDataPublicacao(new Date());
        return livro;
    }

    public static LivroDTO livroDTOValido() {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(1L);
        livroDTO.setTitulo("Titulo teste");
        livroDTO.setAutor("Autor Teste");
        livroDTO.setIsbn("1235647543251");
        livroDTO.setCategoria("Terror");
        livroDTO.setDataPublicacao(new Date());
        return livroDTO;
    }

}
