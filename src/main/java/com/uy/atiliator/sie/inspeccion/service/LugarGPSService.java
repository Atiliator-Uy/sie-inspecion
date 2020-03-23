package com.uy.atiliator.sie.inspeccion.service;

import com.uy.atiliator.sie.inspeccion.service.dto.LugarGPSDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.uy.atiliator.sie.inspeccion.domain.LugarGPS}.
 */
public interface LugarGPSService {

    /**
     * Save a lugarGPS.
     *
     * @param lugarGPSDTO the entity to save.
     * @return the persisted entity.
     */
    LugarGPSDTO save(LugarGPSDTO lugarGPSDTO);

    /**
     * Get all the lugarGPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LugarGPSDTO> findAll(Pageable pageable);

    /**
     * Get the "id" lugarGPS.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LugarGPSDTO> findOne(Long id);

    /**
     * Delete the "id" lugarGPS.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the lugarGPS corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LugarGPSDTO> search(String query, Pageable pageable);
}
