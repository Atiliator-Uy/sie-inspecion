package com.uy.atiliator.sie.inspeccion.service;

import com.uy.atiliator.sie.inspeccion.service.dto.EvidenciaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.uy.atiliator.sie.inspeccion.domain.Evidencia}.
 */
public interface EvidenciaService {

    /**
     * Save a evidencia.
     *
     * @param evidenciaDTO the entity to save.
     * @return the persisted entity.
     */
    EvidenciaDTO save(EvidenciaDTO evidenciaDTO);

    /**
     * Get all the evidencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvidenciaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evidencia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvidenciaDTO> findOne(Long id);

    /**
     * Delete the "id" evidencia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the evidencia corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvidenciaDTO> search(String query, Pageable pageable);
}
