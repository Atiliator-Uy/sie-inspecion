package com.uy.atiliator.sie.inspeccion.repository;

import com.uy.atiliator.sie.inspeccion.domain.Evidencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Evidencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {
}
