package com.uy.atiliator.sie.inspeccion.repository;

import com.uy.atiliator.sie.inspeccion.domain.Inspector;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inspector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
}
