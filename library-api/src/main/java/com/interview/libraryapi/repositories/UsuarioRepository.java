package com.interview.libraryapi.repositories;

import com.interview.libraryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT user FROM Usuario user WHERE LOWER(user.nome) LIKE %:nome%")
    List<Usuario> procurarPorNome(@Param("nome") String nome);

}
