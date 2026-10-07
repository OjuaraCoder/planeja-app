package com.ojuara.planeja.dominio.cartao.mapper;

import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversão entre CartaoEntity e DTOs.
 */
@Mapper(componentModel = "spring")
public interface CartaoMapper {

    /**
     * Converte um CartaoFormDto para uma entidade CartaoEntity.
     */
    CartaoEntity toEntity(CartaoFormDto form);

    /**
     * Converte uma entidade CartaoEntity para um CartaoDetalheDto.
     */
    CartaoDetalheDto toDetalheDto(CartaoEntity entity);

    /**
     * Atualiza uma entidade CartaoEntity existente com os dados de um CartaoFormDto.
     */
    void update(@MappingTarget CartaoEntity entity, CartaoFormDto dadosAtualizacao);
}
