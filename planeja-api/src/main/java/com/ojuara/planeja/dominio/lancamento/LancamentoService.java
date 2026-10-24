package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.dominio.cartao.CartaoRepository;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import com.ojuara.planeja.dominio.categoria.CategoriaRepository;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoFormDto;
import com.ojuara.planeja.dominio.lancamento.model.LancamentoEntity;
import com.ojuara.planeja.dominio.lancamento.model.LancamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LancamentoValidator lancamentoValidator;

    @Autowired
    private LancamentoMapper lancamentoMapper;


    public LancamentoDetalheDto criar(LancamentoFormDto form){

        CategoriaEntity categoria = categoriaRepository
                .findById(form.categoriaId())
                .orElse(null);

        CartaoEntity cartao = null;
        if(form.cartaoId() != null){
            cartao = cartaoRepository
                    .findById(form.cartaoId())
                    .orElse(null);
        }

        var result = lancamentoValidator.validar(form,categoria,cartao);
        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        LancamentoEntity entity = lancamentoMapper.toEntity(form, categoria, cartao);
        lancamentoRepository.save(entity);
        return lancamentoMapper.toDetalheDto(entity);
    }


}
