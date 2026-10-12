package com.ojuara.planeja.dominio.lancamento.model;

public enum TipoLancamentoEnum {

    RECEITA("Receita"),
    DESPESA("Despesa");

    private final String descricao;

    TipoLancamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
