package com.interview.libraryapi.repositories;

import com.interview.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT livro FROM Livro livro WHERE LOWER(livro.titulo) LIKE %:titulo%")
    List<Livro> procurarLivroPorTitulo(@Param("titulo") String titulo);
}
