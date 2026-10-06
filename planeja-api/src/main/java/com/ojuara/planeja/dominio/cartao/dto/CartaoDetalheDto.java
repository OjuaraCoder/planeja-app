package com.ojuara.planeja.dominio.cartao.dto;

import com.ojuara.planeja.dominio.cartao.enums.BandeiraCartaoEnum;

import java.time.LocalDateTime;

public record CartaoDetalheDto(String id,
                               String nome,
                               BandeiraCartaoEnum bandeira,
                               LocalDateTime dataCriacao) {
}
