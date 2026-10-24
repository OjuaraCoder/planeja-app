package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.dominio.cartao.CartaoService;
import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.categoria.CategoriaService;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoFormDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lancamentos")
@CrossOrigin("*")
public class LancamentoController {

    @Autowired
    private LancamentoService service;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CartaoService cartaoService;

//    @PostMapping
//    public ResponseEntity<LancamentoDetalheDto> criar(@RequestBody @Valid LancamentoFormDto dto){
//        var detalhes = service.criar(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoDetalheDto criarAlternativo(@RequestBody @Valid LancamentoFormDto dto){
        return service.criar(dto);
    }

    @GetMapping("categorias-ativas")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaDetalheDto> listarCartegoriasAtivas(){
        return categoriaService.listarCategoriasAtivas();
    }

    @GetMapping("cartoes-ativos")
    @ResponseStatus(HttpStatus.OK)
    public List<CartaoDetalheDto> listarCartoesAtivos(){
        return cartaoService.listarCartoesAtivos();
    }

}
