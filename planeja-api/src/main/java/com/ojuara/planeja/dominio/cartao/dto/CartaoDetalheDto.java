package com.ojuara.planeja.dominio.cartao.dto;

import com.ojuara.planeja.common.enums.BandeiraCartaoEnum;

import java.time.LocalDateTime;

public record CartaoDetalheDto(String id,
                               String nome,
                               BandeiraCartaoEnum bandeira,
                               LocalDateTime dataCriacao) {
}
