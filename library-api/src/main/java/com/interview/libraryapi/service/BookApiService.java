package com.interview.libraryapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.interview.libraryapi.data.dto.v1.LivroDTO;

public interface BookApiService {

    public JsonNode procurarLivrosBookApi(String titulo) throws JsonProcessingException;

    public LivroDTO salvarLivroApiPorId(String id) throws JsonProcessingException;
}
