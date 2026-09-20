package com.ojuara.planeja.common.validation;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException() {
        super("Registro não encontrado");
    }
}
