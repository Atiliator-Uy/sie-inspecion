package com.uy.atiliator.sie.inspeccion.repository;

import com.uy.atiliator.sie.inspeccion.domain.HojaRuta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HojaRuta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HojaRutaRepository extends JpaRepository<HojaRuta, Long> {
}
