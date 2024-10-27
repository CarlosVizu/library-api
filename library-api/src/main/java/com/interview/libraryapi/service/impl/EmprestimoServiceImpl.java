package com.interview.libraryapi.service.impl;

import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.util.StatusEnum;
import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;
import com.interview.libraryapi.exceptions.EmprestimoNaoRealizadoException;
import com.interview.libraryapi.exceptions.ParameterUpdateInvalid;
import com.interview.libraryapi.exceptions.ResourceNotFoundException;
import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.model.Usuario;
import com.interview.libraryapi.repositories.EmprestimoRepository;
import com.interview.libraryapi.service.EmprestimoService;
import com.interview.libraryapi.service.LivroService;
import com.interview.libraryapi.service.UsuarioService;
import com.interview.libraryapi.util.FormatValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        if(!islivroDisponivel(livro.getId()))
            throw new EmprestimoNaoRealizadoException("O Livro informado não está disponível para empréstimo.");

        if(!emprestimoDTO.getStatus().equals((StatusEnum.DISPONIVEL.getDescricao())) && !emprestimoDTO.getStatus().equals(StatusEnum.INDISPONIVEL.getDescricao()))
            throw new ParameterUpdateInvalid("Emprestimo inválido: Informar 'DISPONIVEL' ou 'INDISPONIVEL' no campo Status");


        Emprestimo emprestimo = repository.save(new Emprestimo(emprestimoDTO, usuario, livro));
        return new EmprestimoDTO(emprestimo);
    }

    public EmprestimoDTO atualizarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Usuario usuario = usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId());
        Livro livro = livroService.validarLivroExiste(emprestimoDTO.getLivroId());
        Emprestimo emprestimo = validarEmprestimo(emprestimoDTO.getId());

        validarRegrasAtualizacaoEmprestimo(emprestimoDTO, emprestimo);

        repository.save(new Emprestimo(emprestimoDTO, usuario, livro));
        return emprestimoDTO;
    }

    public boolean islivroDisponivel(Long livroId) {
        Page<Emprestimo> resultado = repository.procurarUltimoEmprestimoPorLivro(livroId, PageRequest.of(0, 1));
        Emprestimo emprestimo = null;
        if(resultado.hasContent())
            emprestimo = resultado.getContent().get(0);

        if(emprestimo != null) {
        //Se há registro de emprestimo, então o único status de disponibilidade é o DISPONIVEL_DEVOLVIDO
            if(emprestimo.getStatus().equalsIgnoreCase(StatusEnum.DISPONIVEL.getDescricao())) {
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

    private void validarRegrasAtualizacaoEmprestimo(EmprestimoDTO emprestimoDTO, Emprestimo emprestimo) {
        if(!emprestimoDTO.getLivroId().equals(emprestimo.getLivro().getId()))
            throw new ParameterUpdateInvalid("Atualização inválida: Não é possível alterar o Livro emprestado.");

        if(!emprestimoDTO.getUsuarioId().equals(emprestimo.getUsuario().getId()))
            throw new ParameterUpdateInvalid("Atualização inválida: Não é possível alterar o Usuário de empréstimo.");

        if(!FormatValidate.isDatasIguais(emprestimoDTO.getDataEmprestimo(), emprestimo.getDataEmprestimo()))
            throw new ParameterUpdateInvalid("Atualização inválida: Não é possível alterar a Data de Empréstimo.");

        if(!emprestimoDTO.getStatus().equals((StatusEnum.DISPONIVEL.getDescricao())) && !emprestimoDTO.getStatus().equals(StatusEnum.INDISPONIVEL.getDescricao()))
            throw new ParameterUpdateInvalid("Atualização inválida: Informar 'DISPONIVEL' ou 'INDISPONIVEL' no campo Status");

        if(!emprestimoDTO.getDataDevolucao().equals(emprestimo.getDataDevolucao()))
            if(FormatValidate.validarDataAnteriorHoje(emprestimoDTO.getDataDevolucao())) {
                throw new ParameterUpdateInvalid("Atualização inválida: A data de devolução deve ser igual ou posterior à data atual.");
            };
    }

    public List<LivroDTO> recomendarLivros(Long usuarioId) {
        List<Livro> historicoLivros = repository.procurarTodosLivrosEmprestadosPorUsuario(usuarioId);

        List<String> categoria = new ArrayList<>();
        List<Long> livrosEmprestados = new ArrayList<>();

        for(Livro livro : historicoLivros) {
            categoria.add(livro.getCategoria());
            livrosEmprestados.add(livro.getId());
        }

        String categoriaFavorita = null;

        if(!categoria.isEmpty()) {
            categoriaFavorita = categoria.stream()
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .get()
                    .getKey();
        }
        return livroService.retornarLivrosRecomendados(categoriaFavorita, livrosEmprestados);
    }




}
