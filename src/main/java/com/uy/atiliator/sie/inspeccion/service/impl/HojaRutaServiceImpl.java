package com.uy.atiliator.sie.inspeccion.service.impl;

import com.uy.atiliator.sie.inspeccion.service.HojaRutaService;
import com.uy.atiliator.sie.inspeccion.domain.HojaRuta;
import com.uy.atiliator.sie.inspeccion.repository.HojaRutaRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.HojaRutaSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.dto.HojaRutaDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.HojaRutaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link HojaRuta}.
 */
@Service
@Transactional
public class HojaRutaServiceImpl implements HojaRutaService {

    private final Logger log = LoggerFactory.getLogger(HojaRutaServiceImpl.class);

    private final HojaRutaRepository hojaRutaRepository;

    private final HojaRutaMapper hojaRutaMapper;

    private final HojaRutaSearchRepository hojaRutaSearchRepository;

    public HojaRutaServiceImpl(HojaRutaRepository hojaRutaRepository, HojaRutaMapper hojaRutaMapper, HojaRutaSearchRepository hojaRutaSearchRepository) {
        this.hojaRutaRepository = hojaRutaRepository;
        this.hojaRutaMapper = hojaRutaMapper;
        this.hojaRutaSearchRepository = hojaRutaSearchRepository;
    }

    /**
     * Save a hojaRuta.
     *
     * @param hojaRutaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HojaRutaDTO save(HojaRutaDTO hojaRutaDTO) {
        log.debug("Request to save HojaRuta : {}", hojaRutaDTO);
        HojaRuta hojaRuta = hojaRutaMapper.toEntity(hojaRutaDTO);
        hojaRuta = hojaRutaRepository.save(hojaRuta);
        HojaRutaDTO result = hojaRutaMapper.toDto(hojaRuta);
        hojaRutaSearchRepository.save(hojaRuta);
        return result;
    }

    /**
     * Get all the hojaRutas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HojaRutaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HojaRutas");
        return hojaRutaRepository.findAll(pageable)
            .map(hojaRutaMapper::toDto);
    }

    /**
     * Get one hojaRuta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HojaRutaDTO> findOne(Long id) {
        log.debug("Request to get HojaRuta : {}", id);
        return hojaRutaRepository.findById(id)
            .map(hojaRutaMapper::toDto);
    }

    /**
     * Delete the hojaRuta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HojaRuta : {}", id);
        hojaRutaRepository.deleteById(id);
        hojaRutaSearchRepository.deleteById(id);
    }

    /**
     * Search for the hojaRuta corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HojaRutaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HojaRutas for query {}", query);
        return hojaRutaSearchRepository.search(queryStringQuery(query), pageable)
            .map(hojaRutaMapper::toDto);
    }
}
