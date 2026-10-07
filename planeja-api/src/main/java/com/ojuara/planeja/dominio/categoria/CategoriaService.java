package com.ojuara.planeja.dominio.categoria;

import com.ojuara.planeja.common.exceptions.RegistroNaoEncontradoException;
import com.ojuara.planeja.common.exceptions.ValidationException;
import com.ojuara.planeja.common.validation.ValidationResult;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaDetalheDto;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaFormDto;
import com.ojuara.planeja.dominio.categoria.mapper.CategoriaMapper;
import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
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

    public void mudarStatusCategoria(Long id, boolean ativo) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);

        categoria.setAtivo(ativo);
        categoriaRepository.save(categoria);
    }

}
