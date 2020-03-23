package com.uy.atiliator.sie.inspeccion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class LugarGPSDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LugarGPSDTO.class);
        LugarGPSDTO lugarGPSDTO1 = new LugarGPSDTO();
        lugarGPSDTO1.setId(1L);
        LugarGPSDTO lugarGPSDTO2 = new LugarGPSDTO();
        assertThat(lugarGPSDTO1).isNotEqualTo(lugarGPSDTO2);
        lugarGPSDTO2.setId(lugarGPSDTO1.getId());
        assertThat(lugarGPSDTO1).isEqualTo(lugarGPSDTO2);
        lugarGPSDTO2.setId(2L);
        assertThat(lugarGPSDTO1).isNotEqualTo(lugarGPSDTO2);
        lugarGPSDTO1.setId(null);
        assertThat(lugarGPSDTO1).isNotEqualTo(lugarGPSDTO2);
    }
}
