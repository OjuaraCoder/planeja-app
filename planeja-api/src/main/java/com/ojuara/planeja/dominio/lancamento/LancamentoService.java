package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.dominio.cartao.CartaoRepository;
import com.ojuara.planeja.dominio.categoria.CategoriaRepository;
import com.ojuara.planeja.dominio.lancamento.model.LancamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private LancamentoValidator lancamentoValidator;

    @Autowired
    private LancamentoMapper lancamentoMapper;



}
