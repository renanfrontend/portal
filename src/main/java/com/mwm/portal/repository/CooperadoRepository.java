package com.mwm.portal.repository;

import com.mwm.portal.model.Cooperado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperadoRepository extends JpaRepository<Cooperado, Long> {
}