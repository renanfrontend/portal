package com.mwm.portal.repository;

import com.mwm.portal.model.Coleta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ColetaRepository extends JpaRepository<Coleta, UUID> {
}