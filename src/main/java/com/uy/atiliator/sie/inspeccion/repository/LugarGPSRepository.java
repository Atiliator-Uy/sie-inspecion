package com.uy.atiliator.sie.inspeccion.repository;

import com.uy.atiliator.sie.inspeccion.domain.LugarGPS;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LugarGPS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LugarGPSRepository extends JpaRepository<LugarGPS, Long> {
}
