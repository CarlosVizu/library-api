package com.interview.libraryapi.controller;

import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.service.LivroService;
import com.interview.libraryapi.util.MediaType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livro/v1")
public class LivroController {

    @Autowired
    private LivroService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON})
    public List<LivroDTO> procurarLivrosPorTitulo(@Param(value = "titulo") String titulo) {
        return service.procurarLivroPorTitulo(titulo);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON})
    public void salvarLivro(@Valid @RequestBody LivroDTO livroDTO) {
        service.salvarLivro(livroDTO);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON})
    public LivroDTO atualizarLivro(@Valid @RequestBody LivroDTO livroDTO) {
        return service.atualizarLivro(livroDTO);
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON})
    public void deletarLivro(@Param(value = "id") Long id) {
        service.deletarLivro(id);
    }
}
