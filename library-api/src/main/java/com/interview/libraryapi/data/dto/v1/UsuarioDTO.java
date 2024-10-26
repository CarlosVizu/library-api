package com.interview.libraryapi.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.interview.libraryapi.model.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Date;

@JsonPropertyOrder({"id", "nome", "email", "dataCadastro", "telefone"})
public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull(message = "O campo 'Nome' é obrigatório")
    private String nome;

    @NotNull(message = "O campo 'Email' é obrigatório")
    private String email;

    @NotNull(message = "O campo 'Data Cadastro' é obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataCadastro;

    @NotNull(message = "O campo 'Telefone' é obrigatório")
    @Pattern(regexp = "^[0-9]+$", message = "O campo 'Telefone' aceita somente números")
    private String telefone;

    public UsuarioDTO(Usuario user) {
        id = user.getId();
        nome = user.getNome();
        email = user.getEmail();
        dataCadastro = user.getDataCadastro();
        telefone = user.getTelefone();
    }

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
