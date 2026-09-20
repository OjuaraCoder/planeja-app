package com.ojuara.planeja.common.exceptions;

import com.ojuara.planeja.common.validation.CampoInvalido;

import java.util.List;


/**
 *
 */
public class ValidationException extends RuntimeException {

    private List<CampoInvalido> camposInvalidos;

    public ValidationException(List<CampoInvalido> camposInvalidos) {
        super();
        this.camposInvalidos = camposInvalidos;
    }


    public List<CampoInvalido> getCamposInvalidos() {
        return camposInvalidos;
    }
}
