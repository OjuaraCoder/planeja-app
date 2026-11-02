package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.dominio.lancamento.dto.LancamentoDetalheDto;
import com.ojuara.planeja.dominio.lancamento.model.TipoLancamentoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ext.javatime.DateTimeParseException;

import java.time.YearMonth;

@RestController
@RequestMapping("/lancamentos")
public class ListaLancamentoController {

    @Autowired
    private LancamentoService service;

    @GetMapping
    public Page<LancamentoDetalheDto> listar ( @RequestParam(value="page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value="size", required = false, defaultValue = "15") int size,
                                               @RequestParam(value="mes", required = false) String mes,
                                               @RequestParam(value="tipoLancamento", required = false) String tipoLancamento,
                                               @RequestParam(value="categoria-id", required = false) Long categoriaId){

        var pageRequest = PageRequest.of(page,size);
        var mesAno = parseMes(mes);
        var tipoLancamentoSelecionado = parseTipo(tipoLancamento);

        return service.listar(pageRequest, mesAno, tipoLancamentoSelecionado, categoriaId);
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
