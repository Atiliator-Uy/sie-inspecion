package com.uy.atiliator.sie.inspeccion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EvidenciaMapperTest {

    private EvidenciaMapper evidenciaMapper;

    @BeforeEach
    public void setUp() {
        evidenciaMapper = new EvidenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(evidenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(evidenciaMapper.fromId(null)).isNull();
    }
}
