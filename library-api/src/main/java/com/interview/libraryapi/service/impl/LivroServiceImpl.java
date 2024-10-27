package com.interview.libraryapi.service.impl;

import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.repositories.LivroRepository;
import com.interview.libraryapi.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroServiceImpl implements LivroService {

    @Autowired
    LivroRepository repository;

    public LivroDTO salvarLivro(LivroDTO livroDTO) {
        Livro livro = repository.save(new Livro(livroDTO));
        return new LivroDTO(livro);
    }

    public List<LivroDTO> procurarLivroPorTitulo(String nome) {
        List<Livro> livros = repository.procurarLivroPorTitulo(nome.toLowerCase());
        if(livros.isEmpty() || nome.isEmpty()) {
            throw new com.interview.libraryapi.exceptions.ResourceNotFoundException("Nenhum livro foi encontrado com este titulo.");
        }

        List<LivroDTO> livroDTOlist = new ArrayList<>();
        for(Livro livro : livros) {
            livroDTOlist.add(new LivroDTO(livro));
        }
        return livroDTOlist;
    }

    public LivroDTO atualizarLivro(LivroDTO livroDTO) {
        validarLivroExiste(livroDTO.getId());
        repository.save(new Livro(livroDTO));
        return livroDTO;
    }

    public void deletarLivro(Long id) {
        Livro livro = validarLivroExiste(id);
        repository.delete(livro);
    }

    private Livro validarLivroExiste(Long id) {
        Optional<Livro> livro = repository.findById(id);
        if(!livro.isPresent()) {
            throw new com.interview.libraryapi.exceptions.ResourceNotFoundException("Nenhum livro encontrado.");
        }
        return livro.get();
    }

}
