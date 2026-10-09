package com.ojuara.planeja.dominio.categoria;

import com.ojuara.planeja.common.validation.CampoInvalido;
import com.ojuara.planeja.common.validation.ValidationResult;
import com.ojuara.planeja.dominio.categoria.dto.CategoriaFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaValidator {

    @Autowired
    private CategoriaRepository repository;

    public ValidationResult validar(CategoriaFormDto categoriaFormDto, Long idCategoria) {
        var result = ValidationResult.novo();
        var existeCategoriaCadastrada = repository.existsByNome(categoriaFormDto.nome());

        if(existeCategoriaCadastrada){
            result.add(new CampoInvalido("nome", "Categoria já cadastrada."));
        }

        return result;
    }

}
