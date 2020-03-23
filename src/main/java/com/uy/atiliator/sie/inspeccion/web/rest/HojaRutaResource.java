package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.service.HojaRutaService;
import com.uy.atiliator.sie.inspeccion.web.rest.errors.BadRequestAlertException;
import com.uy.atiliator.sie.inspeccion.service.dto.HojaRutaDTO;

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
 * REST controller for managing {@link com.uy.atiliator.sie.inspeccion.domain.HojaRuta}.
 */
@RestController
@RequestMapping("/api")
public class HojaRutaResource {

    private final Logger log = LoggerFactory.getLogger(HojaRutaResource.class);

    private static final String ENTITY_NAME = "inspeccionHojaRuta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HojaRutaService hojaRutaService;

    public HojaRutaResource(HojaRutaService hojaRutaService) {
        this.hojaRutaService = hojaRutaService;
    }

    /**
     * {@code POST  /hoja-rutas} : Create a new hojaRuta.
     *
     * @param hojaRutaDTO the hojaRutaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hojaRutaDTO, or with status {@code 400 (Bad Request)} if the hojaRuta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hoja-rutas")
    public ResponseEntity<HojaRutaDTO> createHojaRuta(@RequestBody HojaRutaDTO hojaRutaDTO) throws URISyntaxException {
        log.debug("REST request to save HojaRuta : {}", hojaRutaDTO);
        if (hojaRutaDTO.getId() != null) {
            throw new BadRequestAlertException("A new hojaRuta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HojaRutaDTO result = hojaRutaService.save(hojaRutaDTO);
        return ResponseEntity.created(new URI("/api/hoja-rutas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hoja-rutas} : Updates an existing hojaRuta.
     *
     * @param hojaRutaDTO the hojaRutaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hojaRutaDTO,
     * or with status {@code 400 (Bad Request)} if the hojaRutaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hojaRutaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hoja-rutas")
    public ResponseEntity<HojaRutaDTO> updateHojaRuta(@RequestBody HojaRutaDTO hojaRutaDTO) throws URISyntaxException {
        log.debug("REST request to update HojaRuta : {}", hojaRutaDTO);
        if (hojaRutaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HojaRutaDTO result = hojaRutaService.save(hojaRutaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hojaRutaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hoja-rutas} : get all the hojaRutas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hojaRutas in body.
     */
    @GetMapping("/hoja-rutas")
    public ResponseEntity<List<HojaRutaDTO>> getAllHojaRutas(Pageable pageable) {
        log.debug("REST request to get a page of HojaRutas");
        Page<HojaRutaDTO> page = hojaRutaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hoja-rutas/:id} : get the "id" hojaRuta.
     *
     * @param id the id of the hojaRutaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hojaRutaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hoja-rutas/{id}")
    public ResponseEntity<HojaRutaDTO> getHojaRuta(@PathVariable Long id) {
        log.debug("REST request to get HojaRuta : {}", id);
        Optional<HojaRutaDTO> hojaRutaDTO = hojaRutaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hojaRutaDTO);
    }

    /**
     * {@code DELETE  /hoja-rutas/:id} : delete the "id" hojaRuta.
     *
     * @param id the id of the hojaRutaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hoja-rutas/{id}")
    public ResponseEntity<Void> deleteHojaRuta(@PathVariable Long id) {
        log.debug("REST request to delete HojaRuta : {}", id);
        hojaRutaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/hoja-rutas?query=:query} : search for the hojaRuta corresponding
     * to the query.
     *
     * @param query the query of the hojaRuta search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/hoja-rutas")
    public ResponseEntity<List<HojaRutaDTO>> searchHojaRutas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HojaRutas for query {}", query);
        Page<HojaRutaDTO> page = hojaRutaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
