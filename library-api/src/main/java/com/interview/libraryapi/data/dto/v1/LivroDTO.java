package com.interview.libraryapi.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.interview.libraryapi.model.Livro;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Date;

@JsonPropertyOrder({"id", "titulo", "isbn", "dataPublicacao", "categoria"})
public class LivroDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull(message = "O campo 'Titulo' é obrigatório")
    private String titulo;

    @NotNull(message = "O campo 'Autor' é obrigatório")
    private String autor;

    @NotNull(message = "O campo 'ISBN' é obrigatório")
    @Pattern(regexp = "^[0-9]+$", message = "O campo 'ISBN' aceita somente números")
    private String isbn;

    @NotNull(message = "O campo 'Data Publicação' é obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPublicacao;

    @NotNull(message = "O campo 'Categoria' é obrigatório")
    private String categoria;

    public LivroDTO() {
    }

    public LivroDTO(Livro livro) {
        id = livro.getId();
        titulo = livro.getTitulo();
        isbn = livro.getIsbn();
        dataPublicacao = livro.getDataPublicacao();
        categoria = livro.getCategoria();
        autor = livro.getAutor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
