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

    /**
     * Cria uma categoria com os dados informados.
     *
     * @param novaCategoria dados da categoria a ser criada
     * @return resposta com os dados da categoria criada e status HTTP 201
     */
    @PostMapping
    public ResponseEntity<CategoriaDetalheDto> criarCategoria(@Valid @RequestBody
                                                              CategoriaFormDto novaCategoria) {
        CategoriaDetalheDto detalhe = service.criarCategoria(novaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhe);
    }

    /**
     * Lista as categorias de forma paginada.
     *
     * @param page número da página, começando em zero
     * @param size quantidade de categorias por página
     * @return página com os dados das categorias
     */
    @GetMapping
    public Page<CategoriaDetalheDto> listarCategorias(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size){

        var pageResult = PageRequest.of(page, size);
        return service.listarCategorias(pageResult);
    }

    /**
     * Busca uma categoria pelo identificador.
     *
     * @param id identificador da categoria
     * @return resposta com os dados da categoria encontrada
     */
    @GetMapping("{id}")
    public ResponseEntity<CategoriaDetalheDto> obterCategoria(@PathVariable("id") Long id){
        var result = service.obterCategoria(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Atualiza os dados de uma categoria existente.
     *
     * @param id identificador da categoria a ser atualizada
     * @param categoriaAtualizada novos dados da categoria
     * @return resposta sem conteúdo, com status HTTP 204
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaFormDto categoriaAtualizada) {
        service.atualizarCategoria(id,categoriaAtualizada);
        return ResponseEntity.noContent().build();
    }

    /**
     * Alterna o status de uma categoria entre ativo e inativo.
     *
     * @param id identificador da categoria
     * @return resposta sem conteúdo, com status HTTP 204
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatusCategoria(@PathVariable Long id) {
        service.mudarStatusCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
