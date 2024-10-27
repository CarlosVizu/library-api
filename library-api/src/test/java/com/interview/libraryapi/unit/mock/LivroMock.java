package com.interview.libraryapi.unit.mock;

import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static Livro livroValido(Long id, String titulo, String autor, String isbn, String categoria, Date dataPublicacao) {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setCategoria(categoria);
        livro.setDataPublicacao(dataPublicacao);
        return livro;
    }

    public static List<Livro> listaDeHistoricoLivrosEmprestados() {
        List<Livro> livros = new ArrayList<>();
        livros.add(livroValido(2L, "O Morro dos Ventos Uivantes", "Emily Brontë", "9780141439556", "Romance", new Date(102, 0, 15)));
        livros.add(livroValido(5L, "Crime e Castigo", "Fyodor Dostoevsky", "9780143058144", "Clássico", new Date(102, 8, 1)));
        livros.add(livroValido(12L, "Guerra e Paz", "Leo Tolstoy", "9780670034697", "Histórico", new Date(102, 9, 1)));
        livros.add(livroValido(17L, "A Metamorfose", "Franz Kafka", "9780143105244", "Ficção", new Date(109, 0, 20)));
        livros.add(livroValido(18L, "O Processo", "Franz Kafka", "9780141182902", "Ficção", new Date(99, 4, 1)));
        return livros;
    }

    public static Page<Livro> listaRecomendacaoFiccao() {
        List<Livro> livros = new ArrayList<>();
        livros.add(livroValido(4L, "Cem Anos de Solidão", "Gabriel Garcia Marquez", "9780060883287", "Ficção", new Date(102, 0, 15)));
        livros.add(livroValido(9L, "O Apanhador no Campo de Centeio", "J.D. Salinger", "9780316769488", "Ficção", new Date(102, 8, 1)));
        livros.add(livroValido(15L, "Ulisses", "James Joyce", "9780141182803", "Ficção", new Date(102, 9, 1)));

        Page< Livro > page = new PageImpl<>(livros);
        return page;
    }

}
