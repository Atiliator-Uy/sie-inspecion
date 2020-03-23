package com.uy.atiliator.sie.inspeccion.service;

import com.uy.atiliator.sie.inspeccion.service.dto.InspectorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.uy.atiliator.sie.inspeccion.domain.Inspector}.
 */
public interface InspectorService {

    /**
     * Save a inspector.
     *
     * @param inspectorDTO the entity to save.
     * @return the persisted entity.
     */
    InspectorDTO save(InspectorDTO inspectorDTO);

    /**
     * Get all the inspectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InspectorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" inspector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InspectorDTO> findOne(Long id);

    /**
     * Delete the "id" inspector.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the inspector corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InspectorDTO> search(String query, Pageable pageable);
}
