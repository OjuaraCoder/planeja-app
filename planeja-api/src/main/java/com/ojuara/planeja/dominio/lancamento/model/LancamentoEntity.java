package com.ojuara.planeja.dominio.lancamento.model;

import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "lancamento")
@Getter
@Setter
public class LancamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_id", nullable = false)
    private CartaoEntity cartao;

    @Column(nullable = false,name = "desc_lancamento")
    @Enumerated(EnumType.STRING)
    private TipoLancamentoEnum tipoLancamento;

    @Column(nullable = false,name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(nullable = false,name = "valor_lancamento")
    private BigDecimal valorLancamento;


}
