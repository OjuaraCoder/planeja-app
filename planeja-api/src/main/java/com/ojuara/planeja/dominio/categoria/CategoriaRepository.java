package com.ojuara.planeja.dominio.categoria;

import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    boolean existsByNome(String nome);

    Collection<CategoriaEntity> findByAtivoTrue();
}
