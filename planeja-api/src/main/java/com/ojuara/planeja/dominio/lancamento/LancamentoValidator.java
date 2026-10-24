package com.ojuara.planeja.dominio.lancamento;


import com.ojuara.planeja.common.validation.CampoInvalido;
import com.ojuara.planeja.common.validation.ValidationResult;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoFormDto;
import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import org.springframework.stereotype.Component;

@Component
public class LancamentoValidator {

    public ValidationResult validar(LancamentoFormDto lancFormDto,
                                    CategoriaEntity categoria,
                                    CartaoEntity cartao) {

        var result = ValidationResult.novo();

        if(categoria == null){
            result.add(new CampoInvalido("categoriaId","Categoria não encontrada"));
        } else if(!categoria.isAtivo()){
            result.add(new CampoInvalido("categoriaId", "Categoria inativa"));
        }

        if(lancFormDto.tipoLancamento() == TipoLancamentoEnum.RECEITA && cartao != null){
            result.add(new CampoInvalido("cartaoId","Lancamento do tipo RECEITA não aceita cartão"));
        }

        if(lancFormDto.tipoLancamento() == TipoLancamentoEnum.DESPESA && lancFormDto.cartaoId() != null){
            if(cartao == null){
                result.add(new CampoInvalido("cartaoId","Cartão não encontrado"));
            } else if(!cartao.isAtivo()){
                result.add(new CampoInvalido("cartaoId","Cartao inativo"));
            }
        }

        return result;

    }
}
