package com.mwm.portal.repository;

import com.mwm.portal.model.QualidadeDejetos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface QualidadeRepository extends JpaRepository<QualidadeDejetos, UUID> {
}