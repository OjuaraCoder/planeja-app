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
public class CartaoController {

    @Autowired
    private CartaoService service;


    /**
     * 
     * @param form
     * @return
     */
    @PostMapping
    public ResponseEntity<CartaoDetalheDto> criar(@RequestBody @Valid CartaoFormDto form) {
        CartaoDetalheDto detalhe = service.criar(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhe);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<CartaoDetalheDto> obterDetalhe(@PathVariable("id") UUID id){
        var result = service.obterDetalhe(id);
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @param id
     * @param formAtualizacao
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarCartao(@PathVariable("id") UUID id, @RequestBody @Valid CartaoFormDto formAtualizacao){
        service.atualizarCartao(id, formAtualizacao);
        return ResponseEntity.noContent().build();
    }

}
