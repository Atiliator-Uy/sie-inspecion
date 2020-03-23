package com.uy.atiliator.sie.inspeccion.service.impl;

import com.uy.atiliator.sie.inspeccion.service.InspectorService;
import com.uy.atiliator.sie.inspeccion.domain.Inspector;
import com.uy.atiliator.sie.inspeccion.repository.InspectorRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.InspectorSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.dto.InspectorDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.InspectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Inspector}.
 */
@Service
@Transactional
public class InspectorServiceImpl implements InspectorService {

    private final Logger log = LoggerFactory.getLogger(InspectorServiceImpl.class);

    private final InspectorRepository inspectorRepository;

    private final InspectorMapper inspectorMapper;

    private final InspectorSearchRepository inspectorSearchRepository;

    public InspectorServiceImpl(InspectorRepository inspectorRepository, InspectorMapper inspectorMapper, InspectorSearchRepository inspectorSearchRepository) {
        this.inspectorRepository = inspectorRepository;
        this.inspectorMapper = inspectorMapper;
        this.inspectorSearchRepository = inspectorSearchRepository;
    }

    /**
     * Save a inspector.
     *
     * @param inspectorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InspectorDTO save(InspectorDTO inspectorDTO) {
        log.debug("Request to save Inspector : {}", inspectorDTO);
        Inspector inspector = inspectorMapper.toEntity(inspectorDTO);
        inspector = inspectorRepository.save(inspector);
        InspectorDTO result = inspectorMapper.toDto(inspector);
        inspectorSearchRepository.save(inspector);
        return result;
    }

    /**
     * Get all the inspectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InspectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inspectors");
        return inspectorRepository.findAll(pageable)
            .map(inspectorMapper::toDto);
    }

    /**
     * Get one inspector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InspectorDTO> findOne(Long id) {
        log.debug("Request to get Inspector : {}", id);
        return inspectorRepository.findById(id)
            .map(inspectorMapper::toDto);
    }

    /**
     * Delete the inspector by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inspector : {}", id);
        inspectorRepository.deleteById(id);
        inspectorSearchRepository.deleteById(id);
    }

    /**
     * Search for the inspector corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InspectorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Inspectors for query {}", query);
        return inspectorSearchRepository.search(queryStringQuery(query), pageable)
            .map(inspectorMapper::toDto);
    }
}
