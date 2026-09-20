package com.ojuara.planeja.dominio.cartao;

import com.ojuara.planeja.common.validation.CampoInvalido;
import com.ojuara.planeja.common.validation.ValidationResult;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartaoValidator {

    @Autowired
    private CartaoRepository repository;

    public ValidationResult validar(CartaoFormDto cartaoFormDto, UUID idCartao) {
        var result = ValidationResult.novo();

        if(!repository.findByNomeandId(cartaoFormDto.nome(), idCartao).isEmpty()) {
            result.add(new CampoInvalido("nome", "Já cadastrado."));
        }
        return result;
    }
}
