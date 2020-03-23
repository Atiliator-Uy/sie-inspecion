package com.uy.atiliator.sie.inspeccion.web.rest;

import com.uy.atiliator.sie.inspeccion.InspeccionApp;
import com.uy.atiliator.sie.inspeccion.domain.HojaRuta;
import com.uy.atiliator.sie.inspeccion.repository.HojaRutaRepository;
import com.uy.atiliator.sie.inspeccion.repository.search.HojaRutaSearchRepository;
import com.uy.atiliator.sie.inspeccion.service.HojaRutaService;
import com.uy.atiliator.sie.inspeccion.service.dto.HojaRutaDTO;
import com.uy.atiliator.sie.inspeccion.service.mapper.HojaRutaMapper;

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
 * Integration tests for the {@link HojaRutaResource} REST controller.
 */
@SpringBootTest(classes = InspeccionApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class HojaRutaResourceIT {

    private static final TipoInspeccion DEFAULT_TIPO_INSPECCION = TipoInspeccion.CARRETERA_VIAL;
    private static final TipoInspeccion UPDATED_TIPO_INSPECCION = TipoInspeccion.CARRETERA_POLICIAL;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_HORA_UTC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_HORA_UTC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REALIZADO = false;
    private static final Boolean UPDATED_REALIZADO = true;

    private static final Integer DEFAULT_PRIODIDAD = 1;
    private static final Integer UPDATED_PRIODIDAD = 2;

    @Autowired
    private HojaRutaRepository hojaRutaRepository;

    @Autowired
    private HojaRutaMapper hojaRutaMapper;

    @Autowired
    private HojaRutaService hojaRutaService;

    /**
     * This repository is mocked in the com.uy.atiliator.sie.inspeccion.repository.search test package.
     *
     * @see com.uy.atiliator.sie.inspeccion.repository.search.HojaRutaSearchRepositoryMockConfiguration
     */
    @Autowired
    private HojaRutaSearchRepository mockHojaRutaSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHojaRutaMockMvc;

    private HojaRuta hojaRuta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HojaRuta createEntity(EntityManager em) {
        HojaRuta hojaRuta = new HojaRuta()
            .tipoInspeccion(DEFAULT_TIPO_INSPECCION)
            .fecha(DEFAULT_FECHA)
            .fechaHoraUTC(DEFAULT_FECHA_HORA_UTC)
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .realizado(DEFAULT_REALIZADO)
            .priodidad(DEFAULT_PRIODIDAD);
        return hojaRuta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HojaRuta createUpdatedEntity(EntityManager em) {
        HojaRuta hojaRuta = new HojaRuta()
            .tipoInspeccion(UPDATED_TIPO_INSPECCION)
            .fecha(UPDATED_FECHA)
            .fechaHoraUTC(UPDATED_FECHA_HORA_UTC)
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .realizado(UPDATED_REALIZADO)
            .priodidad(UPDATED_PRIODIDAD);
        return hojaRuta;
    }

    @BeforeEach
    public void initTest() {
        hojaRuta = createEntity(em);
    }

    @Test
    @Transactional
    public void createHojaRuta() throws Exception {
        int databaseSizeBeforeCreate = hojaRutaRepository.findAll().size();

        // Create the HojaRuta
        HojaRutaDTO hojaRutaDTO = hojaRutaMapper.toDto(hojaRuta);
        restHojaRutaMockMvc.perform(post("/api/hoja-rutas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hojaRutaDTO)))
            .andExpect(status().isCreated());

        // Validate the HojaRuta in the database
        List<HojaRuta> hojaRutaList = hojaRutaRepository.findAll();
        assertThat(hojaRutaList).hasSize(databaseSizeBeforeCreate + 1);
        HojaRuta testHojaRuta = hojaRutaList.get(hojaRutaList.size() - 1);
        assertThat(testHojaRuta.getTipoInspeccion()).isEqualTo(DEFAULT_TIPO_INSPECCION);
        assertThat(testHojaRuta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testHojaRuta.getFechaHoraUTC()).isEqualTo(DEFAULT_FECHA_HORA_UTC);
        assertThat(testHojaRuta.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testHojaRuta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testHojaRuta.isRealizado()).isEqualTo(DEFAULT_REALIZADO);
        assertThat(testHojaRuta.getPriodidad()).isEqualTo(DEFAULT_PRIODIDAD);

        // Validate the HojaRuta in Elasticsearch
        verify(mockHojaRutaSearchRepository, times(1)).save(testHojaRuta);
    }

    @Test
    @Transactional
    public void createHojaRutaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hojaRutaRepository.findAll().size();

        // Create the HojaRuta with an existing ID
        hojaRuta.setId(1L);
        HojaRutaDTO hojaRutaDTO = hojaRutaMapper.toDto(hojaRuta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHojaRutaMockMvc.perform(post("/api/hoja-rutas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hojaRutaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HojaRuta in the database
        List<HojaRuta> hojaRutaList = hojaRutaRepository.findAll();
        assertThat(hojaRutaList).hasSize(databaseSizeBeforeCreate);

        // Validate the HojaRuta in Elasticsearch
        verify(mockHojaRutaSearchRepository, times(0)).save(hojaRuta);
    }


    @Test
    @Transactional
    public void getAllHojaRutas() throws Exception {
        // Initialize the database
        hojaRutaRepository.saveAndFlush(hojaRuta);

        // Get all the hojaRutaList
        restHojaRutaMockMvc.perform(get("/api/hoja-rutas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hojaRuta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoInspeccion").value(hasItem(DEFAULT_TIPO_INSPECCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaHoraUTC").value(hasItem(sameInstant(DEFAULT_FECHA_HORA_UTC))))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].realizado").value(hasItem(DEFAULT_REALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].priodidad").value(hasItem(DEFAULT_PRIODIDAD)));
    }
    
    @Test
    @Transactional
    public void getHojaRuta() throws Exception {
        // Initialize the database
        hojaRutaRepository.saveAndFlush(hojaRuta);

        // Get the hojaRuta
        restHojaRutaMockMvc.perform(get("/api/hoja-rutas/{id}", hojaRuta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hojaRuta.getId().intValue()))
            .andExpect(jsonPath("$.tipoInspeccion").value(DEFAULT_TIPO_INSPECCION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.fechaHoraUTC").value(sameInstant(DEFAULT_FECHA_HORA_UTC)))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.realizado").value(DEFAULT_REALIZADO.booleanValue()))
            .andExpect(jsonPath("$.priodidad").value(DEFAULT_PRIODIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingHojaRuta() throws Exception {
        // Get the hojaRuta
        restHojaRutaMockMvc.perform(get("/api/hoja-rutas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHojaRuta() throws Exception {
        // Initialize the database
        hojaRutaRepository.saveAndFlush(hojaRuta);

        int databaseSizeBeforeUpdate = hojaRutaRepository.findAll().size();

        // Update the hojaRuta
        HojaRuta updatedHojaRuta = hojaRutaRepository.findById(hojaRuta.getId()).get();
        // Disconnect from session so that the updates on updatedHojaRuta are not directly saved in db
        em.detach(updatedHojaRuta);
        updatedHojaRuta
            .tipoInspeccion(UPDATED_TIPO_INSPECCION)
            .fecha(UPDATED_FECHA)
            .fechaHoraUTC(UPDATED_FECHA_HORA_UTC)
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .realizado(UPDATED_REALIZADO)
            .priodidad(UPDATED_PRIODIDAD);
        HojaRutaDTO hojaRutaDTO = hojaRutaMapper.toDto(updatedHojaRuta);

        restHojaRutaMockMvc.perform(put("/api/hoja-rutas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hojaRutaDTO)))
            .andExpect(status().isOk());

        // Validate the HojaRuta in the database
        List<HojaRuta> hojaRutaList = hojaRutaRepository.findAll();
        assertThat(hojaRutaList).hasSize(databaseSizeBeforeUpdate);
        HojaRuta testHojaRuta = hojaRutaList.get(hojaRutaList.size() - 1);
        assertThat(testHojaRuta.getTipoInspeccion()).isEqualTo(UPDATED_TIPO_INSPECCION);
        assertThat(testHojaRuta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testHojaRuta.getFechaHoraUTC()).isEqualTo(UPDATED_FECHA_HORA_UTC);
        assertThat(testHojaRuta.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testHojaRuta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testHojaRuta.isRealizado()).isEqualTo(UPDATED_REALIZADO);
        assertThat(testHojaRuta.getPriodidad()).isEqualTo(UPDATED_PRIODIDAD);

        // Validate the HojaRuta in Elasticsearch
        verify(mockHojaRutaSearchRepository, times(1)).save(testHojaRuta);
    }

    @Test
    @Transactional
    public void updateNonExistingHojaRuta() throws Exception {
        int databaseSizeBeforeUpdate = hojaRutaRepository.findAll().size();

        // Create the HojaRuta
        HojaRutaDTO hojaRutaDTO = hojaRutaMapper.toDto(hojaRuta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHojaRutaMockMvc.perform(put("/api/hoja-rutas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hojaRutaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HojaRuta in the database
        List<HojaRuta> hojaRutaList = hojaRutaRepository.findAll();
        assertThat(hojaRutaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HojaRuta in Elasticsearch
        verify(mockHojaRutaSearchRepository, times(0)).save(hojaRuta);
    }

    @Test
    @Transactional
    public void deleteHojaRuta() throws Exception {
        // Initialize the database
        hojaRutaRepository.saveAndFlush(hojaRuta);

        int databaseSizeBeforeDelete = hojaRutaRepository.findAll().size();

        // Delete the hojaRuta
        restHojaRutaMockMvc.perform(delete("/api/hoja-rutas/{id}", hojaRuta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HojaRuta> hojaRutaList = hojaRutaRepository.findAll();
        assertThat(hojaRutaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HojaRuta in Elasticsearch
        verify(mockHojaRutaSearchRepository, times(1)).deleteById(hojaRuta.getId());
    }

    @Test
    @Transactional
    public void searchHojaRuta() throws Exception {
        // Initialize the database
        hojaRutaRepository.saveAndFlush(hojaRuta);
        when(mockHojaRutaSearchRepository.search(queryStringQuery("id:" + hojaRuta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(hojaRuta), PageRequest.of(0, 1), 1));
        // Search the hojaRuta
        restHojaRutaMockMvc.perform(get("/api/_search/hoja-rutas?query=id:" + hojaRuta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hojaRuta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoInspeccion").value(hasItem(DEFAULT_TIPO_INSPECCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaHoraUTC").value(hasItem(sameInstant(DEFAULT_FECHA_HORA_UTC))))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].realizado").value(hasItem(DEFAULT_REALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].priodidad").value(hasItem(DEFAULT_PRIODIDAD)));
    }
}
