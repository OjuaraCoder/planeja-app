package com.ojuara.planeja.dominio.categoria.model;

import com.ojuara.planeja.dominio.categoria.dto.CategoriaDetalheDto;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    /**
     * Converte um CategoriaFormDto para uma entidade CategoriaEntity.
     */
    CategoriaEntity toEntity(CategoriaFormDto form);

    /**
     * Converte uma entidade CategoriaEntity para um CategoriaDetalheDto.
     */
    CategoriaDetalheDto toDetalheDto(CategoriaEntity entity);

    /**
     * Atualiza uma entidade CategoriaEntity existente com os dados de um CategoriaFormDto.
     */
    void update(@MappingTarget CategoriaEntity entity, CategoriaFormDto dadosAtualizacao);

}
