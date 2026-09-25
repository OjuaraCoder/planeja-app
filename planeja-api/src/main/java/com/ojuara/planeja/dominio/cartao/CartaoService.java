package com.ojuara.planeja.dominio.cartao;


import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.common.exceptions.RegistroNaoEncontradoException;
import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import com.ojuara.planeja.dominio.cartao.mapper.CartaoMapper;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private CartaoValidator cartaoValidator;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoMapper cartaoMapper;


    public CartaoDetalheDto criar(CartaoFormDto form) {
        var result = cartaoValidator.validar(form, null);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        CartaoEntity cartao = cartaoMapper.toEntity(form);
        cartaoRepository.save(cartao);
        return cartaoMapper.toDetalhe(cartao);

    }


    public CartaoDetalheDto obterDetalhe(UUID id){
        return cartaoRepository.findById(id)
                .map(cartaoMapper::toDetalhe)
                .orElseThrow(RegistroNaoEncontradoException::new);
    }

    @Transactional
    public void atualizarCartao(UUID id, @Valid CartaoFormDto formAtualizacao) {
        var entity = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        var result = cartaoValidator.validar(formAtualizacao, id);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        cartaoMapper.update(entity, formAtualizacao);

    }

    public void removerCartao(UUID id) {
        var entity = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        cartaoRepository.delete(entity);
    }
}
