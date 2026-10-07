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
    public Page<CategoriaDetalheDto> listarCategorias(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        var pageResult = PageRequest.of(page, size);
        return service.listarCategorias(pageResult);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatusCategoria(@PathVariable Long id, @RequestParam boolean ativo) {
        service.mudarStatusCategoria(id, ativo);
        return ResponseEntity.noContent().build();
    }

}
