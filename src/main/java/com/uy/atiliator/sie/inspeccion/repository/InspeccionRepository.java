package com.uy.atiliator.sie.inspeccion.repository;

import com.uy.atiliator.sie.inspeccion.domain.Inspeccion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inspeccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
}
