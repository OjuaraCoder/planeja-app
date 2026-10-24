package com.ojuara.planeja.dominio.cartao;


import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.cartao.dto.CartaoFormDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
     * Cria um cartão com os dados informados.
     *
     * @param form dados do cartão a ser criado
     * @return resposta com os dados do cartão criado e status HTTP 201
     */
    @PostMapping
    public ResponseEntity<CartaoDetalheDto> criar(@RequestBody @Valid CartaoFormDto form) {
        CartaoDetalheDto detalhe = service.criar(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhe);
    }

    /**
     * Busca os detalhes de um cartão pelo identificador.
     *
     * @param id identificador do cartão
     * @return resposta com os dados do cartão encontrado
     */
    @GetMapping("{id}")
    public ResponseEntity<CartaoDetalheDto> obterDetalhe(@PathVariable("id") Long id){
        var result = service.obterDetalhe(id);
        return ResponseEntity.ok(result);
    }

    /**
     * Lista os cartões de forma paginada.
     *
     * @param page número da página, começando em zero
     * @param size quantidade de cartões por página
     * @return página com os dados dos cartões
     */
    @GetMapping
    public Page<CartaoDetalheDto> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){

        PageRequest pageRequest = PageRequest.of(page, size);
        return service.listarCartoes(pageRequest);
    }


    /**
     * Atualiza os dados de um cartão existente.
     *
     * @param id identificador do cartão a ser atualizado
     * @param formAtualizacao novos dados do cartão
     * @return resposta sem conteúdo, com status HTTP 204
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarCartao(@PathVariable("id") Long id, @RequestBody @Valid CartaoFormDto formAtualizacao){
        service.atualizarCartao(id, formAtualizacao);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove um cartão pelo identificador.
     *
     * @param id identificador do cartão a ser removido
     * @return resposta sem conteúdo, com status HTTP 204
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerCartao(@PathVariable Long id){
        service.removerCartao(id);
        return ResponseEntity.noContent().build();

    }

    /**
     * Alterna o status de um cartão entre ativo e inativo.
     *
     * @param id identificador do cartão
     * @return resposta sem conteúdo, com status HTTP 204
     */
    @PatchMapping("{id}/status")
    public ResponseEntity<Void> mudarStatus(@PathVariable Long id){
        service.mudarStatus(id);
        return ResponseEntity.noContent().build();
    }

}
