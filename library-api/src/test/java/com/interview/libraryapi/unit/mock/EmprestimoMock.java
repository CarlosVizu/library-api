package com.interview.libraryapi.unit.mock;

import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;
import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.util.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmprestimoMock {

    public static Emprestimo emprestimoMock() {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setLivro(LivroMock.livroValido());
        emprestimo.setUsuario(UsuarioMock.usuarioValido());
        emprestimo.setDataEmprestimo(new Date());
        emprestimo.setDataDevolucao(new Date());
        emprestimo.setStatus(StatusEnum.INDISPONIVEL.getDescricao());
        return emprestimo;
    }

    public static EmprestimoDTO emprestimoDTOMock() {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setId(1L);
        emprestimoDTO.setLivroId(1L);
        emprestimoDTO.setUsuarioId(1L);
        emprestimoDTO.setDataEmprestimo(new Date());
        emprestimoDTO.setDataDevolucao(getDateUmDiaAFrente());
        emprestimoDTO.setStatus(StatusEnum.INDISPONIVEL.getDescricao());
        return emprestimoDTO;
    }

    public static Page<Emprestimo> emprestimoPageMockDisponivel() {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setLivro(LivroMock.livroValido());
        emprestimo.setUsuario(UsuarioMock.usuarioValido());
        emprestimo.setDataEmprestimo(new Date());
        emprestimo.setDataDevolucao(new Date());
        emprestimo.setStatus(StatusEnum.DISPONIVEL.getDescricao());

        List<Emprestimo> emprestimoList = new ArrayList<>();
        emprestimoList.add(emprestimo);
        Page<Emprestimo> page = new PageImpl<>(emprestimoList);
        return page;
    }

    public static Page<Emprestimo> emprestimoPageMockIndisponivel() {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setLivro(LivroMock.livroValido());
        emprestimo.setUsuario(UsuarioMock.usuarioValido());
        emprestimo.setDataEmprestimo(new Date());
        emprestimo.setDataDevolucao(new Date());
        emprestimo.setStatus(StatusEnum.INDISPONIVEL.getDescricao());

        List<Emprestimo> emprestimoList = new ArrayList<>();
        emprestimoList.add(emprestimo);
        Page<Emprestimo> page = new PageImpl<>(emprestimoList);
        return page;
    }

    public static Date getDateUmDiaAFrente() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        return date;
    }
}
