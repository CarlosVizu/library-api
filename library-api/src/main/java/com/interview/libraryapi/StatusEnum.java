package com.interview.libraryapi;

public enum StatusEnum {
    DISPONIVEL_DEVOLVIDO("DISPONIVEL: Devolução realizada"),
    INDISPONIVEL("INDISPONÍVEL: Empréstimo"),
    INDISPONIVEL_EXTENDIDO("INDISPONÍVEL: Empréstimo extendido"),
    INDISPONIVEL_PERDIDO("INDISPONÍVEL: Devolução não realizada");

    private String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
