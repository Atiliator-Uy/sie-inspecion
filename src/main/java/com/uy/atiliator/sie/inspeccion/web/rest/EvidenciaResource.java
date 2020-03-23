package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.service.EvidenciaService;
import com.uy.atiliator.sie.inspeccion.web.rest.errors.BadRequestAlertException;
import com.uy.atiliator.sie.inspeccion.service.dto.EvidenciaDTO;

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
 * REST controller for managing {@link com.uy.atiliator.sie.inspeccion.domain.Evidencia}.
 */
@RestController
@RequestMapping("/api")
public class EvidenciaResource {

    private final Logger log = LoggerFactory.getLogger(EvidenciaResource.class);

    private static final String ENTITY_NAME = "inspeccionEvidencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvidenciaService evidenciaService;

    public EvidenciaResource(EvidenciaService evidenciaService) {
        this.evidenciaService = evidenciaService;
    }

    /**
     * {@code POST  /evidencias} : Create a new evidencia.
     *
     * @param evidenciaDTO the evidenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evidenciaDTO, or with status {@code 400 (Bad Request)} if the evidencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evidencias")
    public ResponseEntity<EvidenciaDTO> createEvidencia(@RequestBody EvidenciaDTO evidenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Evidencia : {}", evidenciaDTO);
        if (evidenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new evidencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvidenciaDTO result = evidenciaService.save(evidenciaDTO);
        return ResponseEntity.created(new URI("/api/evidencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evidencias} : Updates an existing evidencia.
     *
     * @param evidenciaDTO the evidenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evidenciaDTO,
     * or with status {@code 400 (Bad Request)} if the evidenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evidenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evidencias")
    public ResponseEntity<EvidenciaDTO> updateEvidencia(@RequestBody EvidenciaDTO evidenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Evidencia : {}", evidenciaDTO);
        if (evidenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvidenciaDTO result = evidenciaService.save(evidenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evidenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evidencias} : get all the evidencias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evidencias in body.
     */
    @GetMapping("/evidencias")
    public ResponseEntity<List<EvidenciaDTO>> getAllEvidencias(Pageable pageable) {
        log.debug("REST request to get a page of Evidencias");
        Page<EvidenciaDTO> page = evidenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evidencias/:id} : get the "id" evidencia.
     *
     * @param id the id of the evidenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evidenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evidencias/{id}")
    public ResponseEntity<EvidenciaDTO> getEvidencia(@PathVariable Long id) {
        log.debug("REST request to get Evidencia : {}", id);
        Optional<EvidenciaDTO> evidenciaDTO = evidenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evidenciaDTO);
    }

    /**
     * {@code DELETE  /evidencias/:id} : delete the "id" evidencia.
     *
     * @param id the id of the evidenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evidencias/{id}")
    public ResponseEntity<Void> deleteEvidencia(@PathVariable Long id) {
        log.debug("REST request to delete Evidencia : {}", id);
        evidenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/evidencias?query=:query} : search for the evidencia corresponding
     * to the query.
     *
     * @param query the query of the evidencia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/evidencias")
    public ResponseEntity<List<EvidenciaDTO>> searchEvidencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Evidencias for query {}", query);
        Page<EvidenciaDTO> page = evidenciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
