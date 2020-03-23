package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.InspeccionApp;
import com.uy.atiliator.sie.inspeccion.domain.Inspeccion;
import com.uy.atiliator.sie.inspeccion.repository.InspeccionRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.InspeccionSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.InspeccionService;
import com.uy.atiliator.sie.inspeccion.service.dto.InspeccionDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.InspeccionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.uy.atiliator.sie.inspeccion.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uy.atiliator.sie.inspeccion.domain.enumeration.TipoInspeccion;
/**
 * Integration tests for the {@link InspeccionResource} REST controller.
 */
@SpringBootTest(classes = InspeccionApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InspeccionResourceIT {

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final TipoInspeccion DEFAULT_TIPO_INSPECCION = TipoInspeccion.CARRETERA_VIAL;
    private static final TipoInspeccion UPDATED_TIPO_INSPECCION = TipoInspeccion.CARRETERA_POLICIAL;

    private static final String DEFAULT_RECOMENDACION = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_HORA_UTC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_HORA_UTC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private InspeccionRepository inspeccionRepository;

    @Autowired
    private InspeccionMapper inspeccionMapper;

    @Autowired
    private InspeccionService inspeccionService;

    /**
     * This repository is mocked in the com.uy.atiliator.sie.inspeccion.repository.search test package.
     *
     * @see com.uy.atiliator.sie.inspeccion.repository.search.InspeccionSearchRepositoryMockConfiguration
     */
    @Autowired
    private InspeccionSearchRepository mockInspeccionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspeccionMockMvc;

    private Inspeccion inspeccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspeccion createEntity(EntityManager em) {
        Inspeccion inspeccion = new Inspeccion()
            .observacion(DEFAULT_OBSERVACION)
            .tipoInspeccion(DEFAULT_TIPO_INSPECCION)
            .recomendacion(DEFAULT_RECOMENDACION)
            .fecha(DEFAULT_FECHA)
            .fechaHoraUTC(DEFAULT_FECHA_HORA_UTC);
        return inspeccion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspeccion createUpdatedEntity(EntityManager em) {
        Inspeccion inspeccion = new Inspeccion()
            .observacion(UPDATED_OBSERVACION)
            .tipoInspeccion(UPDATED_TIPO_INSPECCION)
            .recomendacion(UPDATED_RECOMENDACION)
            .fecha(UPDATED_FECHA)
            .fechaHoraUTC(UPDATED_FECHA_HORA_UTC);
        return inspeccion;
    }

    @BeforeEach
    public void initTest() {
        inspeccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspeccion() throws Exception {
        int databaseSizeBeforeCreate = inspeccionRepository.findAll().size();

        // Create the Inspeccion
        InspeccionDTO inspeccionDTO = inspeccionMapper.toDto(inspeccion);
        restInspeccionMockMvc.perform(post("/api/inspeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspeccionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeCreate + 1);
        Inspeccion testInspeccion = inspeccionList.get(inspeccionList.size() - 1);
        assertThat(testInspeccion.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testInspeccion.getTipoInspeccion()).isEqualTo(DEFAULT_TIPO_INSPECCION);
        assertThat(testInspeccion.getRecomendacion()).isEqualTo(DEFAULT_RECOMENDACION);
        assertThat(testInspeccion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testInspeccion.getFechaHoraUTC()).isEqualTo(DEFAULT_FECHA_HORA_UTC);

        // Validate the Inspeccion in Elasticsearch
        verify(mockInspeccionSearchRepository, times(1)).save(testInspeccion);
    }

    @Test
    @Transactional
    public void createInspeccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspeccionRepository.findAll().size();

        // Create the Inspeccion with an existing ID
        inspeccion.setId(1L);
        InspeccionDTO inspeccionDTO = inspeccionMapper.toDto(inspeccion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspeccionMockMvc.perform(post("/api/inspeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspeccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Inspeccion in Elasticsearch
        verify(mockInspeccionSearchRepository, times(0)).save(inspeccion);
    }


    @Test
    @Transactional
    public void getAllInspeccions() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList
        restInspeccionMockMvc.perform(get("/api/inspeccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspeccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].tipoInspeccion").value(hasItem(DEFAULT_TIPO_INSPECCION.toString())))
            .andExpect(jsonPath("$.[*].recomendacion").value(hasItem(DEFAULT_RECOMENDACION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaHoraUTC").value(hasItem(sameInstant(DEFAULT_FECHA_HORA_UTC))));
    }
    
    @Test
    @Transactional
    public void getInspeccion() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get the inspeccion
        restInspeccionMockMvc.perform(get("/api/inspeccions/{id}", inspeccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspeccion.getId().intValue()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION))
            .andExpect(jsonPath("$.tipoInspeccion").value(DEFAULT_TIPO_INSPECCION.toString()))
            .andExpect(jsonPath("$.recomendacion").value(DEFAULT_RECOMENDACION))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.fechaHoraUTC").value(sameInstant(DEFAULT_FECHA_HORA_UTC)));
    }

    @Test
    @Transactional
    public void getNonExistingInspeccion() throws Exception {
        // Get the inspeccion
        restInspeccionMockMvc.perform(get("/api/inspeccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspeccion() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        int databaseSizeBeforeUpdate = inspeccionRepository.findAll().size();

        // Update the inspeccion
        Inspeccion updatedInspeccion = inspeccionRepository.findById(inspeccion.getId()).get();
        // Disconnect from session so that the updates on updatedInspeccion are not directly saved in db
        em.detach(updatedInspeccion);
        updatedInspeccion
            .observacion(UPDATED_OBSERVACION)
            .tipoInspeccion(UPDATED_TIPO_INSPECCION)
            .recomendacion(UPDATED_RECOMENDACION)
            .fecha(UPDATED_FECHA)
            .fechaHoraUTC(UPDATED_FECHA_HORA_UTC);
        InspeccionDTO inspeccionDTO = inspeccionMapper.toDto(updatedInspeccion);

        restInspeccionMockMvc.perform(put("/api/inspeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspeccionDTO)))
            .andExpect(status().isOk());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeUpdate);
        Inspeccion testInspeccion = inspeccionList.get(inspeccionList.size() - 1);
        assertThat(testInspeccion.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testInspeccion.getTipoInspeccion()).isEqualTo(UPDATED_TIPO_INSPECCION);
        assertThat(testInspeccion.getRecomendacion()).isEqualTo(UPDATED_RECOMENDACION);
        assertThat(testInspeccion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testInspeccion.getFechaHoraUTC()).isEqualTo(UPDATED_FECHA_HORA_UTC);

        // Validate the Inspeccion in Elasticsearch
        verify(mockInspeccionSearchRepository, times(1)).save(testInspeccion);
    }

    @Test
    @Transactional
    public void updateNonExistingInspeccion() throws Exception {
        int databaseSizeBeforeUpdate = inspeccionRepository.findAll().size();

        // Create the Inspeccion
        InspeccionDTO inspeccionDTO = inspeccionMapper.toDto(inspeccion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspeccionMockMvc.perform(put("/api/inspeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspeccionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Inspeccion in Elasticsearch
        verify(mockInspeccionSearchRepository, times(0)).save(inspeccion);
    }

    @Test
    @Transactional
    public void deleteInspeccion() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        int databaseSizeBeforeDelete = inspeccionRepository.findAll().size();

        // Delete the inspeccion
        restInspeccionMockMvc.perform(delete("/api/inspeccions/{id}", inspeccion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Inspeccion in Elasticsearch
        verify(mockInspeccionSearchRepository, times(1)).deleteById(inspeccion.getId());
    }

    @Test
    @Transactional
    public void searchInspeccion() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);
        when(mockInspeccionSearchRepository.search(queryStringQuery("id:" + inspeccion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(inspeccion), PageRequest.of(0, 1), 1));
        // Search the inspeccion
        restInspeccionMockMvc.perform(get("/api/_search/inspeccions?query=id:" + inspeccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspeccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].tipoInspeccion").value(hasItem(DEFAULT_TIPO_INSPECCION.toString())))
            .andExpect(jsonPath("$.[*].recomendacion").value(hasItem(DEFAULT_RECOMENDACION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaHoraUTC").value(hasItem(sameInstant(DEFAULT_FECHA_HORA_UTC))));
    }
}
