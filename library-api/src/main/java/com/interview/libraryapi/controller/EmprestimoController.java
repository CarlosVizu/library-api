package com.interview.libraryapi.controller;

import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;
import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.service.EmprestimoService;
import com.interview.libraryapi.util.MediaType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimo/v1")
public class EmprestimoController {

    @Autowired
    private EmprestimoService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON})
    public void salvarEmprestimo(@Valid @RequestBody EmprestimoDTO emprestimoDTO) {
        service.salvarEmprestimo(emprestimoDTO);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON})
    public EmprestimoDTO atualizarEmprestimo(@Valid @RequestBody EmprestimoDTO emprestimoDTO) {
        return service.atualizarEmprestimo(emprestimoDTO);
    }

    @GetMapping(path = "/recomendar",
            produces = {MediaType.APPLICATION_JSON})
    public List<LivroDTO> recomendarLivros(@Param(value = "usuarioId") Long usuarioId) {
        return service.recomendarLivros(usuarioId);
    }
}
