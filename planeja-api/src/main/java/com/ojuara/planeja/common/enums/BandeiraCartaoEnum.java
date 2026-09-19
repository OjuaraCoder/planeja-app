package com.ojuara.planeja.common.enums;

public enum BandeiraCartaoEnum {

    VISA("Visa"),
    MASTERCARD("Mastercard"),
    AMERICAN_EXPRESS("American Express"),
    ELO("Elo"),
    HIPERCARD("Hipercard");

    private final String descricao;

    BandeiraCartaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }


}
