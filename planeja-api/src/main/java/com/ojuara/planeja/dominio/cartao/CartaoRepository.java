package com.ojuara.planeja.dominio.cartao;

import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para gerenciar entidades de cartão.
 */
public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {

    /**
     * Busca um cartão pelo nome.
     *
     * @param nome nome do cartão a buscar
     * @return cartão encontrado, ou vazio caso não exista cartão com esse nome
     */
    Optional<CartaoEntity> findByNome(String nome);

    /**
     * Busca cartões com o nome informado, excluindo da busca o cartão identificado por {@code id}.
     * Quando {@code id} é nulo, nenhum cartão é excluído.
     *
     * @param nome nome dos cartões a buscar
     * @param id identificador do cartão a excluir da busca, ou {@code null}
     * @return lista dos cartões que correspondem aos critérios
     */
    @Query("""
        select c
            from CartaoEntity c 
           where ( :id is null or c.id != :id )
             and c.nome =:nome 
    """)
    List<CartaoEntity> findByNomeandId(@Param("nome") String nome, @Param("id") UUID id);
}
