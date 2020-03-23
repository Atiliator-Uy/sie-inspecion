package com.uy.atiliator.sie.inspeccion.service.impl;

import com.uy.atiliator.sie.inspeccion.service.EvidenciaService;
import com.uy.atiliator.sie.inspeccion.domain.Evidencia;
import com.uy.atiliator.sie.inspeccion.repository.EvidenciaRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.EvidenciaSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.dto.EvidenciaDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.EvidenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Evidencia}.
 */
@Service
@Transactional
public class EvidenciaServiceImpl implements EvidenciaService {

    private final Logger log = LoggerFactory.getLogger(EvidenciaServiceImpl.class);

    private final EvidenciaRepository evidenciaRepository;

    private final EvidenciaMapper evidenciaMapper;

    private final EvidenciaSearchRepository evidenciaSearchRepository;

    public EvidenciaServiceImpl(EvidenciaRepository evidenciaRepository, EvidenciaMapper evidenciaMapper, EvidenciaSearchRepository evidenciaSearchRepository) {
        this.evidenciaRepository = evidenciaRepository;
        this.evidenciaMapper = evidenciaMapper;
        this.evidenciaSearchRepository = evidenciaSearchRepository;
    }

    /**
     * Save a evidencia.
     *
     * @param evidenciaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EvidenciaDTO save(EvidenciaDTO evidenciaDTO) {
        log.debug("Request to save Evidencia : {}", evidenciaDTO);
        Evidencia evidencia = evidenciaMapper.toEntity(evidenciaDTO);
        evidencia = evidenciaRepository.save(evidencia);
        EvidenciaDTO result = evidenciaMapper.toDto(evidencia);
        evidenciaSearchRepository.save(evidencia);
        return result;
    }

    /**
     * Get all the evidencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EvidenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Evidencias");
        return evidenciaRepository.findAll(pageable)
            .map(evidenciaMapper::toDto);
    }

    /**
     * Get one evidencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EvidenciaDTO> findOne(Long id) {
        log.debug("Request to get Evidencia : {}", id);
        return evidenciaRepository.findById(id)
            .map(evidenciaMapper::toDto);
    }

    /**
     * Delete the evidencia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evidencia : {}", id);
        evidenciaRepository.deleteById(id);
        evidenciaSearchRepository.deleteById(id);
    }

    /**
     * Search for the evidencia corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EvidenciaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Evidencias for query {}", query);
        return evidenciaSearchRepository.search(queryStringQuery(query), pageable)
            .map(evidenciaMapper::toDto);
    }
}
