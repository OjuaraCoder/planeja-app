package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.dominio.lancamento.model.LancamentoEntity;
import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;

public class LancamentoSpecs {

    private LancamentoSpecs() {
        // Construtor privado para evitar instanciamento
    }

    public static Specification<LancamentoEntity> tipoEqual(TipoLancamentoEnum tipoEnum) {
        return (root, query, cb) -> {

            //LancamentoEntity.tipoLancamento = :tipoLancamento
            return cb.equal(root.get("tipoLancamento"), tipoEnum);
        };
    }

    public static Specification<LancamentoEntity> categoriaEqual(Long categoriaId) {
        return (root, query, cb) -> {

            //LancamentoEntity.categoria.id = :categoriaId
            return cb.equal(root.get("categoria").get("id"), categoriaId);
        };
    }

    public static Specification<LancamentoEntity> cartaoEqual(Long cartaoId) {
        return (root, query, cb) -> {

            //LancamentoEntity.cartao.id = :cartaoId
            return cb.equal(root.get("cartao").get("id"), cartaoId);
        };
    }

    public static Specification<LancamentoEntity> mesEqual(YearMonth mes) {
        return (root, query, cb) -> {

            LocalDate inicioMes = mes.atDay(1);
            LocalDate fimMes = mes.atEndOfMonth();

            //LancamentoEntity.data between inicioMes and fimMes
            return cb.between(root.get("data"), inicioMes, fimMes);
        };
    }
}
