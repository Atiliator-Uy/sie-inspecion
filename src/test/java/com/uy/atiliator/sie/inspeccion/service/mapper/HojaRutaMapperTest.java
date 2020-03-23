package com.uy.atiliator.sie.inspeccion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HojaRutaMapperTest {

    private HojaRutaMapper hojaRutaMapper;

    @BeforeEach
    public void setUp() {
        hojaRutaMapper = new HojaRutaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(hojaRutaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hojaRutaMapper.fromId(null)).isNull();
    }
}
