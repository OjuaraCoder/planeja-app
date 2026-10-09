package com.ojuara.planeja.dominio.categoria;

import com.ojuara.planeja.dominio.categoria.dto.CategoriaDetalheDto;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaFormDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categorias")
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    public ResponseEntity<CategoriaDetalheDto> criarCategoria(@Valid @RequestBody
                                                              CategoriaFormDto novaCategoria) {
        CategoriaDetalheDto detalhe = service.criarCategoria(novaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhe);
    }

    @GetMapping
    public Page<CategoriaDetalheDto> listarCategorias(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size){

        var pageResult = PageRequest.of(page, size);
        return service.listarCategorias(pageResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaDetalheDto> obterCategoria(@PathVariable("id") Long id){
        var result = service.obterCategoria(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatusCategoria(@PathVariable Long id, @RequestParam boolean ativo) {
        service.mudarStatusCategoria(id, ativo);
        return ResponseEntity.noContent().build();
    }

}
