package com.uy.atiliator.sie.inspeccion.service.impl;

import com.uy.atiliator.sie.inspeccion.service.InspeccionService;
import com.uy.atiliator.sie.inspeccion.domain.Inspeccion;
import com.uy.atiliator.sie.inspeccion.repository.InspeccionRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.InspeccionSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.dto.InspeccionDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.InspeccionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Inspeccion}.
 */
@Service
@Transactional
public class InspeccionServiceImpl implements InspeccionService {

    private final Logger log = LoggerFactory.getLogger(InspeccionServiceImpl.class);

    private final InspeccionRepository inspeccionRepository;

    private final InspeccionMapper inspeccionMapper;

    private final InspeccionSearchRepository inspeccionSearchRepository;

    public InspeccionServiceImpl(InspeccionRepository inspeccionRepository, InspeccionMapper inspeccionMapper, InspeccionSearchRepository inspeccionSearchRepository) {
        this.inspeccionRepository = inspeccionRepository;
        this.inspeccionMapper = inspeccionMapper;
        this.inspeccionSearchRepository = inspeccionSearchRepository;
    }

    /**
     * Save a inspeccion.
     *
     * @param inspeccionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InspeccionDTO save(InspeccionDTO inspeccionDTO) {
        log.debug("Request to save Inspeccion : {}", inspeccionDTO);
        Inspeccion inspeccion = inspeccionMapper.toEntity(inspeccionDTO);
        inspeccion = inspeccionRepository.save(inspeccion);
        InspeccionDTO result = inspeccionMapper.toDto(inspeccion);
        inspeccionSearchRepository.save(inspeccion);
        return result;
    }

    /**
     * Get all the inspeccions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InspeccionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inspeccions");
        return inspeccionRepository.findAll(pageable)
            .map(inspeccionMapper::toDto);
    }

    /**
     * Get one inspeccion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InspeccionDTO> findOne(Long id) {
        log.debug("Request to get Inspeccion : {}", id);
        return inspeccionRepository.findById(id)
            .map(inspeccionMapper::toDto);
    }

    /**
     * Delete the inspeccion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inspeccion : {}", id);
        inspeccionRepository.deleteById(id);
        inspeccionSearchRepository.deleteById(id);
    }

    /**
     * Search for the inspeccion corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InspeccionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Inspeccions for query {}", query);
        return inspeccionSearchRepository.search(queryStringQuery(query), pageable)
            .map(inspeccionMapper::toDto);
    }
}
