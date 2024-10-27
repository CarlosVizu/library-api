package com.interview.libraryapi.service;

import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;

public interface EmprestimoService {

    public EmprestimoDTO salvarEmprestimo(EmprestimoDTO emprestimoDTO);

    public EmprestimoDTO atualizarEmprestimo(EmprestimoDTO emprestimoDTO);
}
