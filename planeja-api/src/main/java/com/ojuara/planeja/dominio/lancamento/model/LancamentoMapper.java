package com.ojuara.planeja.dominio.lancamento.model;

import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", source = "categoria")
    @Mapping(target = "cartao", source = "cartao")
    @Mapping(target = "valorLancamento", source = "form.valor")
    @Mapping(target = "descricao", source = "form.descricao")
    @Mapping(target = "tipoLancamento", source = "form.tipoLancamento")
    @Mapping(target = "dataLancamento", source = "form.dataLancamento")
    LancamentoEntity toEntity(LancamentoFormDto form, CategoriaEntity categoria, CartaoEntity cartao);



    @Mapping(target="categoriaId", source = "categoria.id")
    @Mapping(target="categoriaNome", source = "categoria.nome")
    @Mapping(target="cartaoId", source = "cartao.id")
    @Mapping(target="cartaoNome", source = "cartao.nome")
    @Mapping(target = "valor", source = "valorLancamento")
    @Mapping(target = "descricao", source = "descricao")
    LancamentoDetalheDto toDetalheDto(LancamentoEntity entity);

    @Mapping(target = "valorLancamento", source = "valor")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "tipoLancamento", source = "tipoLancamento")
    @Mapping(target = "dataLancamento", source = "dataLancamento")
    void update(@MappingTarget LancamentoEntity entity, LancamentoFormDto dadosAtualizacao);
}
