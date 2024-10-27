package com.interview.libraryapi.service;

import com.interview.libraryapi.data.dto.v1.LivroDTO;

import java.util.List;

public interface LivroService {

    public LivroDTO salvarLivro(LivroDTO livroDTO);

    public List<LivroDTO> procurarLivroPorTitulo(String nome);

    public LivroDTO atualizarLivro(LivroDTO livroDTO);

    public void deletarLivro(Long id);
}
