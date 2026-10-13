package com.ojuara.planeja.dominio.lancamento.dto;

import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoFormDto(
        @NotNull(message = "Campo obrigatório")
        Long categoriaId,

        Long cartaoId,

        @NotNull(message = "Campo obrigatório")
        TipoLancamentoEnum tipoLancamento,

        LocalDate dataLancamento,

        @NotNull(message = "Campo obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor
) {
}
