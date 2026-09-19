package com.ojuara.planeja.dominio.cartao.model;

import com.ojuara.planeja.common.enums.BandeiraCartaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cartao")
@Getter
@Setter
public class CartaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "bandeira", nullable = false)
    @Enumerated(EnumType.STRING)
    private BandeiraCartaoEnum bandeira;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist(){
        dataCriacao = LocalDateTime.now();
    }

}
