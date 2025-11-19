package com.mwm.portal.repository;

import com.mwm.portal.model.PortariaRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PortariaRepository extends JpaRepository<PortariaRegistro, UUID> {
    List<PortariaRegistro> findByCategoria(PortariaRegistro.Categoria categoria);
}