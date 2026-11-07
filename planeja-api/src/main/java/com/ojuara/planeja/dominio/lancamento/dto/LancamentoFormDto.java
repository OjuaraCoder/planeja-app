package com.ojuara.planeja.dominio.lancamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoFormDto(
        @NotNull(message = "Campo categoria é obrigatório")
        Long categoriaId,

        Long cartaoId,

        @NotBlank(message = "Campo descrição é obrigatório")
        String descricao,

        @NotNull(message = "Campo tipo de Lançamento é obrigatório")
        TipoLancamentoEnum tipoLancamento,

        @NotNull(message = "Campo data é obrigatório")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataLancamento,

        @NotNull(message = "Campo valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor
) {
}
