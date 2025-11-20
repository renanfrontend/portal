package com.mwm.portal.repository;

import com.mwm.portal.model.Abastecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, UUID> {
}