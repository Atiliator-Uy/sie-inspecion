package com.uy.atiliator.sie.inspeccion.service;

import com.uy.atiliator.sie.inspeccion.service.dto.InspeccionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.uy.atiliator.sie.inspeccion.domain.Inspeccion}.
 */
public interface InspeccionService {

    /**
     * Save a inspeccion.
     *
     * @param inspeccionDTO the entity to save.
     * @return the persisted entity.
     */
    InspeccionDTO save(InspeccionDTO inspeccionDTO);

    /**
     * Get all the inspeccions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InspeccionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" inspeccion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InspeccionDTO> findOne(Long id);

    /**
     * Delete the "id" inspeccion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the inspeccion corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InspeccionDTO> search(String query, Pageable pageable);
}
