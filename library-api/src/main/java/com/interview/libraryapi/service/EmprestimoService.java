package com.interview.libraryapi.service;

import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;
import com.interview.libraryapi.data.dto.v1.LivroDTO;

import java.util.List;

public interface EmprestimoService {

    public EmprestimoDTO salvarEmprestimo(EmprestimoDTO emprestimoDTO);

    public EmprestimoDTO atualizarEmprestimo(EmprestimoDTO emprestimoDTO);

    public List<LivroDTO> recomendarLivros(Long usuarioId);

}
