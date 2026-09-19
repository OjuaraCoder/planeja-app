package com.ojuara.planeja.dominio.cartao;


import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import com.ojuara.planeja.dominio.cartao.mapper.CartaoMapper;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoValidator cartaoValidator;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoMapper cartaoMapper;


    public CartaoDetalheDto criar(CartaoFormDto form) {
        var result = cartaoValidator.validar(form);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        CartaoEntity cartao = cartaoMapper.toEntity(form);
        cartaoRepository.save(cartao);
        return cartaoMapper.toDetalhe(cartao);

    }


}
