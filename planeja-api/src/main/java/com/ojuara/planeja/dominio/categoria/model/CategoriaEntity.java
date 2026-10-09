package com.ojuara.planeja.dominio.categoria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "categoria")
@Getter
@Setter
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "nome", nullable = false, length = 70)
    private String nome;

    @Column(name = "ativo", nullable = false, columnDefinition = "boolean default true")
    private boolean ativo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist(){
        dataCriacao = LocalDateTime.now();
        ativo = true;
    }
}
