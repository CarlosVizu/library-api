package com.interview.libraryapi.repositories;

import com.interview.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT livro FROM Livro livro WHERE LOWER(livro.titulo) LIKE %:titulo%")
    List<Livro> procurarLivroPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT livros from Livro livros WHERE livros.categoria LIKE %:categoria% AND livros.id NOT IN :idLivros")
    public Page<Livro> buscarLivrosRecomendados(@Param("categoria")String categoria, @Param("idLivros")List<Long> historicoLivros, Pageable pageable);

    @Query("SELECT livros from Livro livros order by livros.id desc")
    public Page<Livro> buscarLivrosRecomendados(Pageable pageable);
}
