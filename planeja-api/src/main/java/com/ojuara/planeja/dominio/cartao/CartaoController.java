package com.ojuara.planeja.dominio.cartao;


import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para gerenciar cartões.
 */
@RestController
@RequestMapping("/cartoes")
@CrossOrigin("*")
public class CartaoController {

    @Autowired
    private CartaoService service;


    /**
     * Método utilizado para criar um cartão
     * @param form
     * @return
     */
    @PostMapping
    public ResponseEntity<CartaoDetalheDto> criar(@RequestBody @Valid CartaoFormDto form) {
        CartaoDetalheDto detalhe = service.criar(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhe);
    }

    /**
     * Método utilizado para obter o detalhe de um cartão
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<CartaoDetalheDto> obterDetalhe(@PathVariable("id") UUID id){
        var result = service.obterDetalhe(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Método utilizado para atualizar um cartão
     * @param id
     * @param formAtualizacao
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarCartao(@PathVariable("id") UUID id, @RequestBody @Valid CartaoFormDto formAtualizacao){
        service.atualizarCartao(id, formAtualizacao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerCartao(@PathVariable UUID id){
        service.removerCartao(id);
        return ResponseEntity.noContent().build();

    }

}
