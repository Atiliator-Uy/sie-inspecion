package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.InspeccionApp;
import com.uy.atiliator.sie.inspeccion.domain.Evidencia;
import com.uy.atiliator.sie.inspeccion.repository.EvidenciaRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.EvidenciaSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.EvidenciaService;
import com.uy.atiliator.sie.inspeccion.service.dto.EvidenciaDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.EvidenciaMapper;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link EvidenciaResource} REST controller.
 */
@SpringBootTest(classes = InspeccionApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EvidenciaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private EvidenciaRepository evidenciaRepository;

    @Autowired
    private EvidenciaMapper evidenciaMapper;

    @Autowired
    private EvidenciaService evidenciaService;

    /**
     * This repository is mocked in the com.uy.atiliator.sie.inspeccion.repository.search test package.
     *
     * @see com.uy.atiliator.sie.inspeccion.repository.search.EvidenciaSearchRepositoryMockConfiguration
     */
    @Autowired
    private EvidenciaSearchRepository mockEvidenciaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvidenciaMockMvc;

    private Evidencia evidencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evidencia createEntity(EntityManager em) {
        Evidencia evidencia = new Evidencia()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .url(DEFAULT_URL);
        return evidencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evidencia createUpdatedEntity(EntityManager em) {
        Evidencia evidencia = new Evidencia()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .url(UPDATED_URL);
        return evidencia;
    }

    @BeforeEach
    public void initTest() {
        evidencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvidencia() throws Exception {
        int databaseSizeBeforeCreate = evidenciaRepository.findAll().size();

        // Create the Evidencia
        EvidenciaDTO evidenciaDTO = evidenciaMapper.toDto(evidencia);
        restEvidenciaMockMvc.perform(post("/api/evidencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evidenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Evidencia in the database
        List<Evidencia> evidenciaList = evidenciaRepository.findAll();
        assertThat(evidenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Evidencia testEvidencia = evidenciaList.get(evidenciaList.size() - 1);
        assertThat(testEvidencia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEvidencia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEvidencia.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testEvidencia.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testEvidencia.getUrl()).isEqualTo(DEFAULT_URL);

        // Validate the Evidencia in Elasticsearch
        verify(mockEvidenciaSearchRepository, times(1)).save(testEvidencia);
    }

    @Test
    @Transactional
    public void createEvidenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evidenciaRepository.findAll().size();

        // Create the Evidencia with an existing ID
        evidencia.setId(1L);
        EvidenciaDTO evidenciaDTO = evidenciaMapper.toDto(evidencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvidenciaMockMvc.perform(post("/api/evidencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evidenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evidencia in the database
        List<Evidencia> evidenciaList = evidenciaRepository.findAll();
        assertThat(evidenciaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Evidencia in Elasticsearch
        verify(mockEvidenciaSearchRepository, times(0)).save(evidencia);
    }


    @Test
    @Transactional
    public void getAllEvidencias() throws Exception {
        // Initialize the database
        evidenciaRepository.saveAndFlush(evidencia);

        // Get all the evidenciaList
        restEvidenciaMockMvc.perform(get("/api/evidencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evidencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getEvidencia() throws Exception {
        // Initialize the database
        evidenciaRepository.saveAndFlush(evidencia);

        // Get the evidencia
        restEvidenciaMockMvc.perform(get("/api/evidencias/{id}", evidencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evidencia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingEvidencia() throws Exception {
        // Get the evidencia
        restEvidenciaMockMvc.perform(get("/api/evidencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvidencia() throws Exception {
        // Initialize the database
        evidenciaRepository.saveAndFlush(evidencia);

        int databaseSizeBeforeUpdate = evidenciaRepository.findAll().size();

        // Update the evidencia
        Evidencia updatedEvidencia = evidenciaRepository.findById(evidencia.getId()).get();
        // Disconnect from session so that the updates on updatedEvidencia are not directly saved in db
        em.detach(updatedEvidencia);
        updatedEvidencia
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .url(UPDATED_URL);
        EvidenciaDTO evidenciaDTO = evidenciaMapper.toDto(updatedEvidencia);

        restEvidenciaMockMvc.perform(put("/api/evidencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evidenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Evidencia in the database
        List<Evidencia> evidenciaList = evidenciaRepository.findAll();
        assertThat(evidenciaList).hasSize(databaseSizeBeforeUpdate);
        Evidencia testEvidencia = evidenciaList.get(evidenciaList.size() - 1);
        assertThat(testEvidencia.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEvidencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEvidencia.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testEvidencia.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testEvidencia.getUrl()).isEqualTo(UPDATED_URL);

        // Validate the Evidencia in Elasticsearch
        verify(mockEvidenciaSearchRepository, times(1)).save(testEvidencia);
    }

    @Test
    @Transactional
    public void updateNonExistingEvidencia() throws Exception {
        int databaseSizeBeforeUpdate = evidenciaRepository.findAll().size();

        // Create the Evidencia
        EvidenciaDTO evidenciaDTO = evidenciaMapper.toDto(evidencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvidenciaMockMvc.perform(put("/api/evidencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(evidenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evidencia in the database
        List<Evidencia> evidenciaList = evidenciaRepository.findAll();
        assertThat(evidenciaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Evidencia in Elasticsearch
        verify(mockEvidenciaSearchRepository, times(0)).save(evidencia);
    }

    @Test
    @Transactional
    public void deleteEvidencia() throws Exception {
        // Initialize the database
        evidenciaRepository.saveAndFlush(evidencia);

        int databaseSizeBeforeDelete = evidenciaRepository.findAll().size();

        // Delete the evidencia
        restEvidenciaMockMvc.perform(delete("/api/evidencias/{id}", evidencia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evidencia> evidenciaList = evidenciaRepository.findAll();
        assertThat(evidenciaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Evidencia in Elasticsearch
        verify(mockEvidenciaSearchRepository, times(1)).deleteById(evidencia.getId());
    }

    @Test
    @Transactional
    public void searchEvidencia() throws Exception {
        // Initialize the database
        evidenciaRepository.saveAndFlush(evidencia);
        when(mockEvidenciaSearchRepository.search(queryStringQuery("id:" + evidencia.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(evidencia), PageRequest.of(0, 1), 1));
        // Search the evidencia
        restEvidenciaMockMvc.perform(get("/api/_search/evidencias?query=id:" + evidencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evidencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
}
