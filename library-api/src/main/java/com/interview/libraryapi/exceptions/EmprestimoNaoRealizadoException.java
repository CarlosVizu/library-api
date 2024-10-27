package com.interview.libraryapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmprestimoNaoRealizadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmprestimoNaoRealizadoException(String ex) {
        super(ex);
    }

}
