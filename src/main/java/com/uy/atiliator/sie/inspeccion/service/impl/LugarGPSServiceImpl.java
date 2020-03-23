package com.uy.atiliator.sie.inspeccion.service.impl;

import com.uy.atiliator.sie.inspeccion.service.LugarGPSService;
import com.uy.atiliator.sie.inspeccion.domain.LugarGPS;
import com.uy.atiliator.sie.inspeccion.repository.LugarGPSRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.LugarGPSSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.dto.LugarGPSDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.LugarGPSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LugarGPS}.
 */
@Service
@Transactional
public class LugarGPSServiceImpl implements LugarGPSService {

    private final Logger log = LoggerFactory.getLogger(LugarGPSServiceImpl.class);

    private final LugarGPSRepository lugarGPSRepository;

    private final LugarGPSMapper lugarGPSMapper;

    private final LugarGPSSearchRepository lugarGPSSearchRepository;

    public LugarGPSServiceImpl(LugarGPSRepository lugarGPSRepository, LugarGPSMapper lugarGPSMapper, LugarGPSSearchRepository lugarGPSSearchRepository) {
        this.lugarGPSRepository = lugarGPSRepository;
        this.lugarGPSMapper = lugarGPSMapper;
        this.lugarGPSSearchRepository = lugarGPSSearchRepository;
    }

    /**
     * Save a lugarGPS.
     *
     * @param lugarGPSDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LugarGPSDTO save(LugarGPSDTO lugarGPSDTO) {
        log.debug("Request to save LugarGPS : {}", lugarGPSDTO);
        LugarGPS lugarGPS = lugarGPSMapper.toEntity(lugarGPSDTO);
        lugarGPS = lugarGPSRepository.save(lugarGPS);
        LugarGPSDTO result = lugarGPSMapper.toDto(lugarGPS);
        lugarGPSSearchRepository.save(lugarGPS);
        return result;
    }

    /**
     * Get all the lugarGPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LugarGPSDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LugarGPS");
        return lugarGPSRepository.findAll(pageable)
            .map(lugarGPSMapper::toDto);
    }

    /**
     * Get one lugarGPS by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LugarGPSDTO> findOne(Long id) {
        log.debug("Request to get LugarGPS : {}", id);
        return lugarGPSRepository.findById(id)
            .map(lugarGPSMapper::toDto);
    }

    /**
     * Delete the lugarGPS by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LugarGPS : {}", id);
        lugarGPSRepository.deleteById(id);
        lugarGPSSearchRepository.deleteById(id);
    }

    /**
     * Search for the lugarGPS corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LugarGPSDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LugarGPS for query {}", query);
        return lugarGPSSearchRepository.search(queryStringQuery(query), pageable)
            .map(lugarGPSMapper::toDto);
    }
}
