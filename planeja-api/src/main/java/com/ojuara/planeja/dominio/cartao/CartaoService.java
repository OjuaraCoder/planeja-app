package com.ojuara.planeja.dominio.cartao;


import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.common.exceptions.RegistroNaoEncontradoException;
import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import com.ojuara.planeja.dominio.cartao.mapper.CartaoMapper;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    /**
     * Valida os dados informados, cria e persiste um cartão.
     *
     * @param form dados do cartão a ser criado
     * @return detalhes do cartão criado
     * @throws ValidationException quando os dados do cartão são inválidos
     */
    public CartaoDetalheDto criar(CartaoFormDto form) {
        var result = cartaoValidator.validar(form, null);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        CartaoEntity cartao = cartaoMapper.toEntity(form);
        cartaoRepository.save(cartao);
        return cartaoMapper.toDetalhe(cartao);

    }

    /**
     * Busca um cartão pelo identificador e retorna seus detalhes.
     *
     * @param id identificador do cartão
     * @return detalhes do cartão encontrado
     * @throws RegistroNaoEncontradoException quando não existe cartão com o identificador informado
     */
    public CartaoDetalheDto obterDetalhe(UUID id){
        return cartaoRepository.findById(id)
                .map(cartaoMapper::toDetalhe)
                .orElseThrow(RegistroNaoEncontradoException::new);
    }

    /**
     * Valida e aplica os dados informados ao cartão existente.
     *
     * @param id identificador do cartão a atualizar
     * @param formAtualizacao novos dados do cartão
     * @throws RegistroNaoEncontradoException quando não existe cartão com o identificador informado
     * @throws ValidationException quando os dados de atualização são inválidos
     */
    @Transactional
    public void atualizarCartao(UUID id, @Valid CartaoFormDto formAtualizacao) {
        var entity = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        var result = cartaoValidator.validar(formAtualizacao, id);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        cartaoMapper.update(entity, formAtualizacao);

    }

    /**
     * Remove o cartão identificado.
     *
     * @param id identificador do cartão a remover
     * @throws RegistroNaoEncontradoException quando não existe cartão com o identificador informado
     */
    public void removerCartao(UUID id) {
        var entity = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        cartaoRepository.delete(entity);
    }

    /**
     * Lista os cartões de forma paginada e converte cada registro para detalhes.
     *
     * @param pageRequest parâmetros de página e ordenação
     * @return página com os detalhes dos cartões
     */
    public Page<CartaoDetalheDto> listarCartoes(PageRequest pageRequest){
        return cartaoRepository
                .findAll(pageRequest)
                .map(cartaoMapper::toDetalhe);
    }

    @Transactional
    public void mudarStatus(UUID id){
        var entity = cartaoRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        entity.setAtivo(!entity.isAtivo());

        //opcional
        cartaoRepository.save(entity);
    }
}
