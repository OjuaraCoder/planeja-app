package com.ojuara.planeja.dominio.lancamento;

import com.ojuara.planeja.dominio.lancamento.model.LancamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface LancamentoRepository extends JpaRepository<LancamentoEntity, UUID> {
}
