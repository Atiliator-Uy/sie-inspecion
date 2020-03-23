package com.uy.atiliator.sie.inspeccion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InspeccionMapperTest {

    private InspeccionMapper inspeccionMapper;

    @BeforeEach
    public void setUp() {
        inspeccionMapper = new InspeccionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inspeccionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inspeccionMapper.fromId(null)).isNull();
    }
}
