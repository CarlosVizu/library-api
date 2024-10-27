package com.interview.libraryapi.repositories;

import com.interview.libraryapi.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT emprestimo FROM Emprestimo emprestimo WHERE emprestimo.livro.id = :livro_id ORDER BY emprestimo.id ASC")
    Emprestimo procurarUltimoEmprestimoPorLivro(@Param("livro_id")Long livroId);

}
