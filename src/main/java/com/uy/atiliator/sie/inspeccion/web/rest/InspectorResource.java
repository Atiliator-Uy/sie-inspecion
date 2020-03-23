package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.service.InspectorService;
import com.uy.atiliator.sie.inspeccion.web.rest.errors.BadRequestAlertException;
import com.uy.atiliator.sie.inspeccion.service.dto.InspectorDTO;

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
 * REST controller for managing {@link com.uy.atiliator.sie.inspeccion.domain.Inspector}.
 */
@RestController
@RequestMapping("/api")
public class InspectorResource {

    private final Logger log = LoggerFactory.getLogger(InspectorResource.class);

    private static final String ENTITY_NAME = "inspeccionInspector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectorService inspectorService;

    public InspectorResource(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    /**
     * {@code POST  /inspectors} : Create a new inspector.
     *
     * @param inspectorDTO the inspectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectorDTO, or with status {@code 400 (Bad Request)} if the inspector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspectors")
    public ResponseEntity<InspectorDTO> createInspector(@RequestBody InspectorDTO inspectorDTO) throws URISyntaxException {
        log.debug("REST request to save Inspector : {}", inspectorDTO);
        if (inspectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new inspector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspectorDTO result = inspectorService.save(inspectorDTO);
        return ResponseEntity.created(new URI("/api/inspectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspectors} : Updates an existing inspector.
     *
     * @param inspectorDTO the inspectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectorDTO,
     * or with status {@code 400 (Bad Request)} if the inspectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspectors")
    public ResponseEntity<InspectorDTO> updateInspector(@RequestBody InspectorDTO inspectorDTO) throws URISyntaxException {
        log.debug("REST request to update Inspector : {}", inspectorDTO);
        if (inspectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InspectorDTO result = inspectorService.save(inspectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inspectors} : get all the inspectors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectors in body.
     */
    @GetMapping("/inspectors")
    public ResponseEntity<List<InspectorDTO>> getAllInspectors(Pageable pageable) {
        log.debug("REST request to get a page of Inspectors");
        Page<InspectorDTO> page = inspectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inspectors/:id} : get the "id" inspector.
     *
     * @param id the id of the inspectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspectors/{id}")
    public ResponseEntity<InspectorDTO> getInspector(@PathVariable Long id) {
        log.debug("REST request to get Inspector : {}", id);
        Optional<InspectorDTO> inspectorDTO = inspectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inspectorDTO);
    }

    /**
     * {@code DELETE  /inspectors/:id} : delete the "id" inspector.
     *
     * @param id the id of the inspectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspectors/{id}")
    public ResponseEntity<Void> deleteInspector(@PathVariable Long id) {
        log.debug("REST request to delete Inspector : {}", id);
        inspectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/inspectors?query=:query} : search for the inspector corresponding
     * to the query.
     *
     * @param query the query of the inspector search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/inspectors")
    public ResponseEntity<List<InspectorDTO>> searchInspectors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Inspectors for query {}", query);
        Page<InspectorDTO> page = inspectorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
