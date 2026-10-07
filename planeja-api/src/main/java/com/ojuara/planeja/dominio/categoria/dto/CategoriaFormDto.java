package com.ojuara.planeja.dominio.categoria.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaFormDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,

        @NotBlank(message = "Campo obrigatório")
        String descricao
) { }
