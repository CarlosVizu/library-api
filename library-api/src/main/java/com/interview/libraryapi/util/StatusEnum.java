package com.interview.libraryapi.util;

public enum StatusEnum {
    DISPONIVEL("DISPONIVEL"),
    INDISPONIVEL("INDISPONIVEL");

    private String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
