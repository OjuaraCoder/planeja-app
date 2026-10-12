package com.ojuara.planeja.dominio.categoria;

import com.ojuara.planeja.common.exceptions.RegistroNaoEncontradoException;
import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.common.validation.ValidationResult;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaDetalheDto;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaFormDto;
import com.ojuara.planeja.dominio.categoria.model.CategoriaMapper;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaValidator categoriaValidator;

    @Autowired
    private CategoriaValidator validator;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public CategoriaDetalheDto criarCategoria(CategoriaFormDto categoriaForm) {
        ValidationResult result =  validator.validar(categoriaForm, null);

        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        CategoriaEntity entity = categoriaMapper.toEntity(categoriaForm);
        categoriaRepository.save(entity);

        return categoriaMapper.toDetalheDto(entity);
    }

    public Page<CategoriaDetalheDto> listarCategorias(PageRequest pageRequest){
        return categoriaRepository
                .findAll(pageRequest)
                .map(categoriaMapper::toDetalheDto);
    }

    public void mudarStatusCategoria(Long id) {
        CategoriaEntity categoria = categoriaRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        categoria.setAtivo(!categoria.isAtivo());
        categoriaRepository.save(categoria);
    }

    public CategoriaDetalheDto obterCategoria(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDetalheDto)
                .orElseThrow(RegistroNaoEncontradoException::new);

    }

    @Transactional
    public void atualizarCategoria(Long id, @Valid CategoriaFormDto categoriaAtualizada) {
        CategoriaEntity categoria = categoriaRepository.findById(id).orElseThrow(RegistroNaoEncontradoException::new);
        var result = validator.validar(categoriaAtualizada, null);
        if(result.isInvalido()){
            throw new ValidationException(result.getCamposInvalidos());
        }

        categoriaMapper.update(categoria, categoriaAtualizada);
    }
}
