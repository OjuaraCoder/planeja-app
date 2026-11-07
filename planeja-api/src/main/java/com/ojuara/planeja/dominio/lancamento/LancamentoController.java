package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.dominio.cartao.CartaoService;
import com.ojuara.planeja.dominio.cartao.dto.CartaoDetalheDto;
import com.ojuara.planeja.dominio.categoria.CategoriaService;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoDetalheDto;
import com.ojuara.planeja.dominio.lancamento.dto.LancamentoFormDto;
import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ext.javatime.DateTimeParseException;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
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

    /**
     * Cria um lançamento com os dados informados.
     *
     * @param dto dados do lançamento a ser criado
     * @return dados do lançamento criado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoDetalheDto criarAlternativo(@RequestBody @Valid LancamentoFormDto dto){
        return service.criar(dto);
    }

    /**
     * Lista as categorias ativas disponíveis para um lançamento.
     *
     * @return lista de categorias ativas
     */
    @GetMapping("categorias-ativas")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaDetalheDto> listarCartegoriasAtivas(){
        return categoriaService.listarCategoriasAtivas();
    }

    /**
     * Lista os cartões ativos disponíveis para um lançamento.
     *
     * @return lista de cartões ativos
     */
    @GetMapping("cartoes-ativos")
    @ResponseStatus(HttpStatus.OK)
    public List<CartaoDetalheDto> listarCartoesAtivos(){
        return cartaoService.listarCartoesAtivos();
    }

    /**
     * Lista os Lançamentos realizados.
     *
     * @return lista de cartões ativos
     */
    @GetMapping
    public Page<LancamentoDetalheDto> listar (@RequestParam(value="page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value="size", required = false, defaultValue = "15") int size,
                                              @RequestParam(value="mes", required = false) String mes,
                                              @RequestParam(value="tipoLancamento", required = false) String tipoLancamento,
                                              @RequestParam(value="categoria-id", required = false) Long categoriaId,
                                              @RequestParam(value="cartao-id", required = false) Long cartaoId){

        var pageRequest = PageRequest.of(page,size);
        var mesAno = parseMes(mes);
        var tipoLancamentoSelecionado = parseTipo(tipoLancamento);

        return service.listar(pageRequest, mesAno, tipoLancamentoSelecionado, categoriaId, cartaoId);
    }

    private TipoLancamentoEnum parseTipo(String tipoLancamento){
        if(tipoLancamento == null || tipoLancamento.isBlank()){
            return null;
        }
        try{
            return TipoLancamentoEnum.valueOf(tipoLancamento);
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Parametro 'tipo' invalido");
        }
    }

    //yyyy-MM
    private YearMonth parseMes(String mes){
        if(mes == null || mes.isBlank()){
            return null;
        }
        try{
            return YearMonth.parse(mes);
        }catch (DateTimeParseException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Parametro 'mes' invalido. use o formato yyyy-MM");
        }
    }

}
