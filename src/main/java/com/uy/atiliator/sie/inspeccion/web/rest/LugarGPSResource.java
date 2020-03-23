package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.service.LugarGPSService;
import com.uy.atiliator.sie.inspeccion.web.rest.errors.BadRequestAlertException;
import com.uy.atiliator.sie.inspeccion.service.dto.LugarGPSDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.uy.atiliator.sie.inspeccion.domain.LugarGPS}.
 */
@RestController
@RequestMapping("/api")
public class LugarGPSResource {

    private final Logger log = LoggerFactory.getLogger(LugarGPSResource.class);

    private static final String ENTITY_NAME = "inspeccionLugarGps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LugarGPSService lugarGPSService;

    public LugarGPSResource(LugarGPSService lugarGPSService) {
        this.lugarGPSService = lugarGPSService;
    }

    /**
     * {@code POST  /lugar-gps} : Create a new lugarGPS.
     *
     * @param lugarGPSDTO the lugarGPSDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lugarGPSDTO, or with status {@code 400 (Bad Request)} if the lugarGPS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lugar-gps")
    public ResponseEntity<LugarGPSDTO> createLugarGPS(@RequestBody LugarGPSDTO lugarGPSDTO) throws URISyntaxException {
        log.debug("REST request to save LugarGPS : {}", lugarGPSDTO);
        if (lugarGPSDTO.getId() != null) {
            throw new BadRequestAlertException("A new lugarGPS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LugarGPSDTO result = lugarGPSService.save(lugarGPSDTO);
        return ResponseEntity.created(new URI("/api/lugar-gps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lugar-gps} : Updates an existing lugarGPS.
     *
     * @param lugarGPSDTO the lugarGPSDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lugarGPSDTO,
     * or with status {@code 400 (Bad Request)} if the lugarGPSDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lugarGPSDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lugar-gps")
    public ResponseEntity<LugarGPSDTO> updateLugarGPS(@RequestBody LugarGPSDTO lugarGPSDTO) throws URISyntaxException {
        log.debug("REST request to update LugarGPS : {}", lugarGPSDTO);
        if (lugarGPSDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LugarGPSDTO result = lugarGPSService.save(lugarGPSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lugarGPSDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lugar-gps} : get all the lugarGPS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lugarGPS in body.
     */
    @GetMapping("/lugar-gps")
    public ResponseEntity<List<LugarGPSDTO>> getAllLugarGPS(Pageable pageable) {
        log.debug("REST request to get a page of LugarGPS");
        Page<LugarGPSDTO> page = lugarGPSService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lugar-gps/:id} : get the "id" lugarGPS.
     *
     * @param id the id of the lugarGPSDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lugarGPSDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lugar-gps/{id}")
    public ResponseEntity<LugarGPSDTO> getLugarGPS(@PathVariable Long id) {
        log.debug("REST request to get LugarGPS : {}", id);
        Optional<LugarGPSDTO> lugarGPSDTO = lugarGPSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lugarGPSDTO);
    }

    /**
     * {@code DELETE  /lugar-gps/:id} : delete the "id" lugarGPS.
     *
     * @param id the id of the lugarGPSDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lugar-gps/{id}")
    public ResponseEntity<Void> deleteLugarGPS(@PathVariable Long id) {
        log.debug("REST request to delete LugarGPS : {}", id);
        lugarGPSService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/lugar-gps?query=:query} : search for the lugarGPS corresponding
     * to the query.
     *
     * @param query the query of the lugarGPS search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/lugar-gps")
    public ResponseEntity<List<LugarGPSDTO>> searchLugarGPS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LugarGPS for query {}", query);
        Page<LugarGPSDTO> page = lugarGPSService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
