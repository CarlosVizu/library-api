package com.interview.libraryapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.service.BookApiService;
import com.interview.libraryapi.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/book/v1")
public class BookApiController {

    @Autowired
    BookApiService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON})
    public JsonNode chamarApiGoogle(@Param(value = "titulo") String titulo) throws JsonProcessingException {
        return service.procurarLivrosBookApi(titulo);

    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON})
    public LivroDTO salvarLivroApiPorId(@Param(value = "id") String id) throws JsonProcessingException {
        return service.salvarLivroApiPorId(id);
    }
}
