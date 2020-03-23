package com.uy.atiliator.sie.inspeccion.service;

import com.uy.atiliator.sie.inspeccion.service.dto.HojaRutaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.uy.atiliator.sie.inspeccion.domain.HojaRuta}.
 */
public interface HojaRutaService {

    /**
     * Save a hojaRuta.
     *
     * @param hojaRutaDTO the entity to save.
     * @return the persisted entity.
     */
    HojaRutaDTO save(HojaRutaDTO hojaRutaDTO);

    /**
     * Get all the hojaRutas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HojaRutaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hojaRuta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HojaRutaDTO> findOne(Long id);

    /**
     * Delete the "id" hojaRuta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the hojaRuta corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HojaRutaDTO> search(String query, Pageable pageable);
}
