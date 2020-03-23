package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.service.InspeccionService;
import com.uy.atiliator.sie.inspeccion.web.rest.errors.BadRequestAlertException;
import com.uy.atiliator.sie.inspeccion.service.dto.InspeccionDTO;

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
 * REST controller for managing {@link com.uy.atiliator.sie.inspeccion.domain.Inspeccion}.
 */
@RestController
@RequestMapping("/api")
public class InspeccionResource {

    private final Logger log = LoggerFactory.getLogger(InspeccionResource.class);

    private static final String ENTITY_NAME = "inspeccionInspeccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspeccionService inspeccionService;

    public InspeccionResource(InspeccionService inspeccionService) {
        this.inspeccionService = inspeccionService;
    }

    /**
     * {@code POST  /inspeccions} : Create a new inspeccion.
     *
     * @param inspeccionDTO the inspeccionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspeccionDTO, or with status {@code 400 (Bad Request)} if the inspeccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspeccions")
    public ResponseEntity<InspeccionDTO> createInspeccion(@RequestBody InspeccionDTO inspeccionDTO) throws URISyntaxException {
        log.debug("REST request to save Inspeccion : {}", inspeccionDTO);
        if (inspeccionDTO.getId() != null) {
            throw new BadRequestAlertException("A new inspeccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspeccionDTO result = inspeccionService.save(inspeccionDTO);
        return ResponseEntity.created(new URI("/api/inspeccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspeccions} : Updates an existing inspeccion.
     *
     * @param inspeccionDTO the inspeccionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspeccionDTO,
     * or with status {@code 400 (Bad Request)} if the inspeccionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspeccionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspeccions")
    public ResponseEntity<InspeccionDTO> updateInspeccion(@RequestBody InspeccionDTO inspeccionDTO) throws URISyntaxException {
        log.debug("REST request to update Inspeccion : {}", inspeccionDTO);
        if (inspeccionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InspeccionDTO result = inspeccionService.save(inspeccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspeccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inspeccions} : get all the inspeccions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspeccions in body.
     */
    @GetMapping("/inspeccions")
    public ResponseEntity<List<InspeccionDTO>> getAllInspeccions(Pageable pageable) {
        log.debug("REST request to get a page of Inspeccions");
        Page<InspeccionDTO> page = inspeccionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inspeccions/:id} : get the "id" inspeccion.
     *
     * @param id the id of the inspeccionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspeccionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspeccions/{id}")
    public ResponseEntity<InspeccionDTO> getInspeccion(@PathVariable Long id) {
        log.debug("REST request to get Inspeccion : {}", id);
        Optional<InspeccionDTO> inspeccionDTO = inspeccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inspeccionDTO);
    }

    /**
     * {@code DELETE  /inspeccions/:id} : delete the "id" inspeccion.
     *
     * @param id the id of the inspeccionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspeccions/{id}")
    public ResponseEntity<Void> deleteInspeccion(@PathVariable Long id) {
        log.debug("REST request to delete Inspeccion : {}", id);
        inspeccionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/inspeccions?query=:query} : search for the inspeccion corresponding
     * to the query.
     *
     * @param query the query of the inspeccion search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/inspeccions")
    public ResponseEntity<List<InspeccionDTO>> searchInspeccions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Inspeccions for query {}", query);
        Page<InspeccionDTO> page = inspeccionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
