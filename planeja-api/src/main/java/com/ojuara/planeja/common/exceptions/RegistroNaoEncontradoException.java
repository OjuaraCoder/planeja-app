package com.ojuara.planeja.common.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException() {
        super("Registro não encontrado");
    }
}
