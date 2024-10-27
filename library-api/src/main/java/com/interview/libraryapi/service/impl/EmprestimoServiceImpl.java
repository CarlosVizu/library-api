package com.interview.libraryapi.service.impl;

import com.interview.libraryapi.StatusEnum;
import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;
import com.interview.libraryapi.exceptions.EmprestimoNaoRealizadoException;
import com.interview.libraryapi.exceptions.ResourceNotFoundException;
import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.model.Usuario;
import com.interview.libraryapi.repositories.EmprestimoRepository;
import com.interview.libraryapi.repositories.LivroRepository;
import com.interview.libraryapi.repositories.UsuarioRepository;
import com.interview.libraryapi.service.EmprestimoService;
import com.interview.libraryapi.service.LivroService;
import com.interview.libraryapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    @Autowired
    EmprestimoRepository repository;

    @Autowired
    LivroService livroService;

    @Autowired
    UsuarioService usuarioService;

    public EmprestimoDTO salvarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Usuario usuario = usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId());
        Livro livro = livroService.validarLivroExiste(emprestimoDTO.getLivroId());

        if(!islivroDisponivel(livro.getId())) {
            throw new EmprestimoNaoRealizadoException("O Livro informado não está disponível para empréstimo.");
        }

        Emprestimo emprestimo = repository.save(new Emprestimo(emprestimoDTO, usuario, livro));
        return new EmprestimoDTO(emprestimo);
    }

    public EmprestimoDTO atualizarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Usuario usuario = usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId());
        Livro livro = livroService.validarLivroExiste(emprestimoDTO.getLivroId());
        Emprestimo emprestimo = validarEmprestimo(emprestimoDTO.getId());

        if(!emprestimoDTO.getDataDevolucao().equals(emprestimo.getDataDevolucao())
            && emprestimoDTO.getDataDevolucao().after(emprestimo.getDataDevolucao())) {

            if(emprestimoDTO.getStatus().equalsIgnoreCase(emprestimo.getStatus())) {
                emprestimoDTO.setStatus(StatusEnum.INDISPONIVEL_EXTENDIDO.getDescricao());
            }
        }

        repository.save(new Emprestimo(emprestimoDTO, usuario, livro));
        return emprestimoDTO;
    }

    public boolean islivroDisponivel(Long livroId) {
        Emprestimo emprestimo = repository.procurarUltimoEmprestimoPorLivro(livroId);

        if(emprestimo != null) {
        //Se há registro de emprestimo, então o único status de disponibilidade é o DISPONIVEL_DEVOLVIDO
            if(emprestimo.getStatus().equalsIgnoreCase(StatusEnum.DISPONIVEL_DEVOLVIDO.getDescricao())) {
                return true;
            } else {
                return false;
            }
        }
        //Se não há registro de emprestimo do livro, então ele está disponível.
        return true;
    }

    public Emprestimo validarEmprestimo(Long emprestimoId) {
        Optional<Emprestimo> emprestimo = repository.findById(emprestimoId);
        if(!emprestimo.isPresent()) {
            throw new ResourceNotFoundException("Nenhum empréstimo encontrado.");
        }
        return emprestimo.get();
    }

}
