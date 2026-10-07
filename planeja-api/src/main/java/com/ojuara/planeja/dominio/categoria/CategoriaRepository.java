package com.ojuara.planeja.dominio.categoria;

import com.ojuara.planeja.dominio.categoria.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    @Query("""
        select c
            from CategoriaEntity c 
           where ( :id is null or c.id != :id )
             and c.nome =:nome 
    """)
    List<CategoriaEntity> findByNomeandId(@Param("nome") String nome, @Param("id") Long id);

}
