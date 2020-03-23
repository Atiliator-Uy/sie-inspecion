package com.uy.atiliator.sie.inspeccion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LugarGPSMapperTest {

    private LugarGPSMapper lugarGPSMapper;

    @BeforeEach
    public void setUp() {
        lugarGPSMapper = new LugarGPSMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lugarGPSMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lugarGPSMapper.fromId(null)).isNull();
    }
}
