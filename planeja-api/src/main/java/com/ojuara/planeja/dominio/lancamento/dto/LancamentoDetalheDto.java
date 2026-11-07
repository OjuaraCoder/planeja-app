package com.ojuara.planeja.dominio.lancamento.dto;

import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoDetalheDto (
        String id,
        LocalDate dataLancamento,
        BigDecimal valor,
        TipoLancamentoEnum tipoLancamento,
        Long categoriaId,
        String categoriaNome,
        Long cartaoId,
        String cartaoNome,
        String descricao
) {
}
