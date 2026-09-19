package com.ojuara.planeja.dominio.cartao.dto;

import com.ojuara.planeja.common.enums.BandeiraCartaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartaoFormDto(

        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        BandeiraCartaoEnum bandeira) {


}
