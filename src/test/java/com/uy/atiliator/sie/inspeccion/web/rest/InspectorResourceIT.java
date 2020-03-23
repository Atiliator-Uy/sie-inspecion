package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.InspeccionApp;
import com.uy.atiliator.sie.inspeccion.domain.Inspector;
import com.uy.atiliator.sie.inspeccion.repository.InspectorRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.InspectorSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.InspectorService;
import com.uy.atiliator.sie.inspeccion.service.dto.InspectorDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.InspectorMapper;

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
 * Integration tests for the {@link InspectorResource} REST controller.
 */
@SpringBootTest(classes = InspeccionApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InspectorResourceIT {

    private static final String DEFAULT_ID_USER = "AAAAAAAAAA";
    private static final String UPDATED_ID_USER = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private InspectorRepository inspectorRepository;

    @Autowired
    private InspectorMapper inspectorMapper;

    @Autowired
    private InspectorService inspectorService;

    /**
     * This repository is mocked in the com.uy.atiliator.sie.inspeccion.repository.search test package.
     *
     * @see com.uy.atiliator.sie.inspeccion.repository.search.InspectorSearchRepositoryMockConfiguration
     */
    @Autowired
    private InspectorSearchRepository mockInspectorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspectorMockMvc;

    private Inspector inspector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspector createEntity(EntityManager em) {
        Inspector inspector = new Inspector()
            .idUser(DEFAULT_ID_USER)
            .login(DEFAULT_LOGIN)
            .nombre(DEFAULT_NOMBRE);
        return inspector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspector createUpdatedEntity(EntityManager em) {
        Inspector inspector = new Inspector()
            .idUser(UPDATED_ID_USER)
            .login(UPDATED_LOGIN)
            .nombre(UPDATED_NOMBRE);
        return inspector;
    }

    @BeforeEach
    public void initTest() {
        inspector = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspector() throws Exception {
        int databaseSizeBeforeCreate = inspectorRepository.findAll().size();

        // Create the Inspector
        InspectorDTO inspectorDTO = inspectorMapper.toDto(inspector);
        restInspectorMockMvc.perform(post("/api/inspectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeCreate + 1);
        Inspector testInspector = inspectorList.get(inspectorList.size() - 1);
        assertThat(testInspector.getIdUser()).isEqualTo(DEFAULT_ID_USER);
        assertThat(testInspector.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testInspector.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the Inspector in Elasticsearch
        verify(mockInspectorSearchRepository, times(1)).save(testInspector);
    }

    @Test
    @Transactional
    public void createInspectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectorRepository.findAll().size();

        // Create the Inspector with an existing ID
        inspector.setId(1L);
        InspectorDTO inspectorDTO = inspectorMapper.toDto(inspector);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectorMockMvc.perform(post("/api/inspectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Inspector in Elasticsearch
        verify(mockInspectorSearchRepository, times(0)).save(inspector);
    }


    @Test
    @Transactional
    public void getAllInspectors() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        // Get all the inspectorList
        restInspectorMockMvc.perform(get("/api/inspectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspector.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        // Get the inspector
        restInspectorMockMvc.perform(get("/api/inspectors/{id}", inspector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspector.getId().intValue()))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingInspector() throws Exception {
        // Get the inspector
        restInspectorMockMvc.perform(get("/api/inspectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        int databaseSizeBeforeUpdate = inspectorRepository.findAll().size();

        // Update the inspector
        Inspector updatedInspector = inspectorRepository.findById(inspector.getId()).get();
        // Disconnect from session so that the updates on updatedInspector are not directly saved in db
        em.detach(updatedInspector);
        updatedInspector
            .idUser(UPDATED_ID_USER)
            .login(UPDATED_LOGIN)
            .nombre(UPDATED_NOMBRE);
        InspectorDTO inspectorDTO = inspectorMapper.toDto(updatedInspector);

        restInspectorMockMvc.perform(put("/api/inspectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspectorDTO)))
            .andExpect(status().isOk());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeUpdate);
        Inspector testInspector = inspectorList.get(inspectorList.size() - 1);
        assertThat(testInspector.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testInspector.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testInspector.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the Inspector in Elasticsearch
        verify(mockInspectorSearchRepository, times(1)).save(testInspector);
    }

    @Test
    @Transactional
    public void updateNonExistingInspector() throws Exception {
        int databaseSizeBeforeUpdate = inspectorRepository.findAll().size();

        // Create the Inspector
        InspectorDTO inspectorDTO = inspectorMapper.toDto(inspector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectorMockMvc.perform(put("/api/inspectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inspectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inspector in the database
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Inspector in Elasticsearch
        verify(mockInspectorSearchRepository, times(0)).save(inspector);
    }

    @Test
    @Transactional
    public void deleteInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);

        int databaseSizeBeforeDelete = inspectorRepository.findAll().size();

        // Delete the inspector
        restInspectorMockMvc.perform(delete("/api/inspectors/{id}", inspector.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inspector> inspectorList = inspectorRepository.findAll();
        assertThat(inspectorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Inspector in Elasticsearch
        verify(mockInspectorSearchRepository, times(1)).deleteById(inspector.getId());
    }

    @Test
    @Transactional
    public void searchInspector() throws Exception {
        // Initialize the database
        inspectorRepository.saveAndFlush(inspector);
        when(mockInspectorSearchRepository.search(queryStringQuery("id:" + inspector.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(inspector), PageRequest.of(0, 1), 1));
        // Search the inspector
        restInspectorMockMvc.perform(get("/api/_search/inspectors?query=id:" + inspector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspector.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
}
