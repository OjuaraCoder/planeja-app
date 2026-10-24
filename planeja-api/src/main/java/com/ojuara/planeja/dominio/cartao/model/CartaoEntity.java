package com.ojuara.planeja.dominio.cartao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cartao")
@Getter @Setter
public class CartaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "bandeira", nullable = false)
    @Enumerated(EnumType.STRING)
    private BandeiraCartaoEnum bandeira;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "bol_ativo", nullable = false)
    private boolean ativo;

    @PrePersist
    public void prePersist(){
        ativo = true;
        dataCriacao = LocalDateTime.now();
    }

}
