package com.ojuara.planeja.dominio.lancamento.model;

import com.ojuara.planeja.dominio.categoria.dto.CategoriaFormDto;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    LancamentoEntity toEntity(LancamentoFormDto form);

    LancamentoDetalheDto toDetalheDto(LancamentoEntity entity);

    void update(@MappingTarget CategoriaEntity entity, CategoriaFormDto dadosAtualizacao);
}
