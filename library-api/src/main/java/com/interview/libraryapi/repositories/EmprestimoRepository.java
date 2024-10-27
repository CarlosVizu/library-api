package com.interview.libraryapi.repositories;

import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT emprestimo FROM Emprestimo emprestimo WHERE emprestimo.livro.id = :livro_id ORDER BY emprestimo.id DESC")
    Page<Emprestimo> procurarUltimoEmprestimoPorLivro(@Param("livro_id")Long livroId, Pageable pageable);

    @Query("SELECT livros FROM Emprestimo e JOIN e.livro livros WHERE e.usuario.id = :usuarioId ORDER BY e.dataEmprestimo DESC")
    List<Livro> procurarTodosLivrosEmprestadosPorUsuario(@Param("usuarioId")Long usuarioId);
}
