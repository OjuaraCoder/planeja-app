package com.ojuara.planeja.common.validation;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa o resultado de uma validação, contendo os campos inválidos encontrados.
 */
public class ValidationResult {

    private List<CampoInvalido> camposInvalidos;

    /**
     * Cria um resultado de validação com a lista de campos inválidos informada.
     *
     * @param camposInvalidos campos que falharam na validação
     */
    private ValidationResult(List<CampoInvalido> camposInvalidos) {
        this.camposInvalidos = camposInvalidos;
    }

    /**
     * Cria um resultado de validação inicialmente sem campos inválidos.
     *
     * @return novo resultado de validação vazio
     */
    public static ValidationResult novo(){
        return new ValidationResult(new ArrayList<>());
    }

    /**
     * Adiciona um campo inválido ao resultado da validação.
     *
     * @param campoInvalido campo que falhou na validação
     */
    public void add(CampoInvalido campoInvalido){
        this.camposInvalidos.add(campoInvalido);
    }

    /**
     * Retorna os campos inválidos encontrados.
     *
     * @return lista de campos inválidos
     */
    public List<CampoInvalido> getCamposInvalidos() {
        return camposInvalidos;
    }

    /**
     * Verifica se o resultado contém algum campo inválido.
     *
     * @return {@code true} se houver campos inválidos; caso contrário, {@code false}
     */
    public boolean isInvalido(){
        return !this.camposInvalidos.isEmpty();
    }

}
