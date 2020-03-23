package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.InspeccionApp;
import com.uy.atiliator.sie.inspeccion.domain.LugarGPS;
import com.uy.atiliator.sie.inspeccion.repository.LugarGPSRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.LugarGPSSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.LugarGPSService;
import com.uy.atiliator.sie.inspeccion.service.dto.LugarGPSDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.LugarGPSMapper;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LugarGPSResource} REST controller.
 */
@SpringBootTest(classes = InspeccionApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LugarGPSResourceIT {

    private static final String DEFAULT_ALTITUD = "AAAAAAAAAA";
    private static final String UPDATED_ALTITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private LugarGPSRepository lugarGPSRepository;

    @Autowired
    private LugarGPSMapper lugarGPSMapper;

    @Autowired
    private LugarGPSService lugarGPSService;

    /**
     * This repository is mocked in the com.uy.atiliator.sie.inspeccion.repository.search test package.
     *
     * @see com.uy.atiliator.sie.inspeccion.repository.search.LugarGPSSearchRepositoryMockConfiguration
     */
    @Autowired
    private LugarGPSSearchRepository mockLugarGPSSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLugarGPSMockMvc;

    private LugarGPS lugarGPS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LugarGPS createEntity(EntityManager em) {
        LugarGPS lugarGPS = new LugarGPS()
            .altitud(DEFAULT_ALTITUD)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .nombre(DEFAULT_NOMBRE);
        return lugarGPS;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LugarGPS createUpdatedEntity(EntityManager em) {
        LugarGPS lugarGPS = new LugarGPS()
            .altitud(UPDATED_ALTITUD)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .nombre(UPDATED_NOMBRE);
        return lugarGPS;
    }

    @BeforeEach
    public void initTest() {
        lugarGPS = createEntity(em);
    }

    @Test
    @Transactional
    public void createLugarGPS() throws Exception {
        int databaseSizeBeforeCreate = lugarGPSRepository.findAll().size();

        // Create the LugarGPS
        LugarGPSDTO lugarGPSDTO = lugarGPSMapper.toDto(lugarGPS);
        restLugarGPSMockMvc.perform(post("/api/lugar-gps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lugarGPSDTO)))
            .andExpect(status().isCreated());

        // Validate the LugarGPS in the database
        List<LugarGPS> lugarGPSList = lugarGPSRepository.findAll();
        assertThat(lugarGPSList).hasSize(databaseSizeBeforeCreate + 1);
        LugarGPS testLugarGPS = lugarGPSList.get(lugarGPSList.size() - 1);
        assertThat(testLugarGPS.getAltitud()).isEqualTo(DEFAULT_ALTITUD);
        assertThat(testLugarGPS.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testLugarGPS.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testLugarGPS.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the LugarGPS in Elasticsearch
        verify(mockLugarGPSSearchRepository, times(1)).save(testLugarGPS);
    }

    @Test
    @Transactional
    public void createLugarGPSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lugarGPSRepository.findAll().size();

        // Create the LugarGPS with an existing ID
        lugarGPS.setId(1L);
        LugarGPSDTO lugarGPSDTO = lugarGPSMapper.toDto(lugarGPS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLugarGPSMockMvc.perform(post("/api/lugar-gps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lugarGPSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LugarGPS in the database
        List<LugarGPS> lugarGPSList = lugarGPSRepository.findAll();
        assertThat(lugarGPSList).hasSize(databaseSizeBeforeCreate);

        // Validate the LugarGPS in Elasticsearch
        verify(mockLugarGPSSearchRepository, times(0)).save(lugarGPS);
    }


    @Test
    @Transactional
    public void getAllLugarGPS() throws Exception {
        // Initialize the database
        lugarGPSRepository.saveAndFlush(lugarGPS);

        // Get all the lugarGPSList
        restLugarGPSMockMvc.perform(get("/api/lugar-gps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lugarGPS.getId().intValue())))
            .andExpect(jsonPath("$.[*].altitud").value(hasItem(DEFAULT_ALTITUD)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getLugarGPS() throws Exception {
        // Initialize the database
        lugarGPSRepository.saveAndFlush(lugarGPS);

        // Get the lugarGPS
        restLugarGPSMockMvc.perform(get("/api/lugar-gps/{id}", lugarGPS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lugarGPS.getId().intValue()))
            .andExpect(jsonPath("$.altitud").value(DEFAULT_ALTITUD))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingLugarGPS() throws Exception {
        // Get the lugarGPS
        restLugarGPSMockMvc.perform(get("/api/lugar-gps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLugarGPS() throws Exception {
        // Initialize the database
        lugarGPSRepository.saveAndFlush(lugarGPS);

        int databaseSizeBeforeUpdate = lugarGPSRepository.findAll().size();

        // Update the lugarGPS
        LugarGPS updatedLugarGPS = lugarGPSRepository.findById(lugarGPS.getId()).get();
        // Disconnect from session so that the updates on updatedLugarGPS are not directly saved in db
        em.detach(updatedLugarGPS);
        updatedLugarGPS
            .altitud(UPDATED_ALTITUD)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .nombre(UPDATED_NOMBRE);
        LugarGPSDTO lugarGPSDTO = lugarGPSMapper.toDto(updatedLugarGPS);

        restLugarGPSMockMvc.perform(put("/api/lugar-gps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lugarGPSDTO)))
            .andExpect(status().isOk());

        // Validate the LugarGPS in the database
        List<LugarGPS> lugarGPSList = lugarGPSRepository.findAll();
        assertThat(lugarGPSList).hasSize(databaseSizeBeforeUpdate);
        LugarGPS testLugarGPS = lugarGPSList.get(lugarGPSList.size() - 1);
        assertThat(testLugarGPS.getAltitud()).isEqualTo(UPDATED_ALTITUD);
        assertThat(testLugarGPS.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testLugarGPS.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testLugarGPS.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the LugarGPS in Elasticsearch
        verify(mockLugarGPSSearchRepository, times(1)).save(testLugarGPS);
    }

    @Test
    @Transactional
    public void updateNonExistingLugarGPS() throws Exception {
        int databaseSizeBeforeUpdate = lugarGPSRepository.findAll().size();

        // Create the LugarGPS
        LugarGPSDTO lugarGPSDTO = lugarGPSMapper.toDto(lugarGPS);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLugarGPSMockMvc.perform(put("/api/lugar-gps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lugarGPSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LugarGPS in the database
        List<LugarGPS> lugarGPSList = lugarGPSRepository.findAll();
        assertThat(lugarGPSList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LugarGPS in Elasticsearch
        verify(mockLugarGPSSearchRepository, times(0)).save(lugarGPS);
    }

    @Test
    @Transactional
    public void deleteLugarGPS() throws Exception {
        // Initialize the database
        lugarGPSRepository.saveAndFlush(lugarGPS);

        int databaseSizeBeforeDelete = lugarGPSRepository.findAll().size();

        // Delete the lugarGPS
        restLugarGPSMockMvc.perform(delete("/api/lugar-gps/{id}", lugarGPS.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LugarGPS> lugarGPSList = lugarGPSRepository.findAll();
        assertThat(lugarGPSList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LugarGPS in Elasticsearch
        verify(mockLugarGPSSearchRepository, times(1)).deleteById(lugarGPS.getId());
    }

    @Test
    @Transactional
    public void searchLugarGPS() throws Exception {
        // Initialize the database
        lugarGPSRepository.saveAndFlush(lugarGPS);
        when(mockLugarGPSSearchRepository.search(queryStringQuery("id:" + lugarGPS.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lugarGPS), PageRequest.of(0, 1), 1));
        // Search the lugarGPS
        restLugarGPSMockMvc.perform(get("/api/_search/lugar-gps?query=id:" + lugarGPS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lugarGPS.getId().intValue())))
            .andExpect(jsonPath("$.[*].altitud").value(hasItem(DEFAULT_ALTITUD)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
}
