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
import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

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

    public Page<LancamentoDetalheDto> listar(
            PageRequest pageRequest,
            YearMonth mes,
            TipoLancamentoEnum tipoEnum,
            Long categoriaId,
            Long cartaoId){

        // select * from Lancamento where 1 = 1
        Specification<LancamentoEntity> spec = Specification.unrestricted();

        if(tipoEnum != null){
            //and tipo =:tipo
            spec = spec.and(LancamentoSpecs.tipoEqual(tipoEnum));
        }

        if(mes != null){
            //and mes =:mes
            spec = spec.and(LancamentoSpecs.mesEqual(mes));
        }

        if(categoriaId != null){
            //and categoriaId =:categoriaId
            spec = spec.and(LancamentoSpecs.categoriaEqual(categoriaId));
        }

        if(cartaoId != null){
            spec = spec.and(LancamentoSpecs.cartaoEqual(cartaoId));
        }

        var result = lancamentoRepository.findAll(spec, pageRequest);
        return result.map(lancamentoMapper::toDetalheDto);
    }

}
