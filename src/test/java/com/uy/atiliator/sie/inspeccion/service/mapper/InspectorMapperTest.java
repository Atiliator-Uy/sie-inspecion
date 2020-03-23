package com.uy.atiliator.sie.inspeccion.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InspectorMapperTest {

    private InspectorMapper inspectorMapper;

    @BeforeEach
    public void setUp() {
        inspectorMapper = new InspectorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inspectorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inspectorMapper.fromId(null)).isNull();
    }
}
