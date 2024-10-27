package com.interview.libraryapi.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@JsonPropertyOrder({"id", "livroId", "tituloLivro", "autor", "dataEmprestimo","dataDevolucao", "status", "usuarioId", "nomeUsuario"})
public class EmprestimoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "O campo 'Usuario' é obrigatório")
    private Long usuarioId;

    private String nomeUsuario;

    @NotNull(message = "O campo 'Livro' é obrigatório")
    private Long livroId;

    private String tituloLivro;

    private String autor;

    @NotNull(message = "O campo 'Data Empréstimo' é obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataEmprestimo;

    @NotNull(message = "O campo 'Data Devolução' é obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataDevolucao;

    @NotNull(message = "O campo 'Status' é obrigatório")
    private String status;

    public EmprestimoDTO() {
    }

    public EmprestimoDTO(Emprestimo emprestimo) {
        id = emprestimo.getId();
        usuarioId = emprestimo.getUsuario().getId();
        nomeUsuario = emprestimo.getUsuario().getNome();
        livroId = emprestimo.getLivro().getId();
        tituloLivro = emprestimo.getLivro().getTitulo();
        autor = emprestimo.getLivro().getAutor();
        dataEmprestimo = emprestimo.getDataEmprestimo();
        dataDevolucao = emprestimo.getDataDevolucao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
