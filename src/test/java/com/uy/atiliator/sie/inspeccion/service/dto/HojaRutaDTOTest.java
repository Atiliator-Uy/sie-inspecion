package com.uy.atiliator.sie.inspeccion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class HojaRutaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HojaRutaDTO.class);
        HojaRutaDTO hojaRutaDTO1 = new HojaRutaDTO();
        hojaRutaDTO1.setId(1L);
        HojaRutaDTO hojaRutaDTO2 = new HojaRutaDTO();
        assertThat(hojaRutaDTO1).isNotEqualTo(hojaRutaDTO2);
        hojaRutaDTO2.setId(hojaRutaDTO1.getId());
        assertThat(hojaRutaDTO1).isEqualTo(hojaRutaDTO2);
        hojaRutaDTO2.setId(2L);
        assertThat(hojaRutaDTO1).isNotEqualTo(hojaRutaDTO2);
        hojaRutaDTO1.setId(null);
        assertThat(hojaRutaDTO1).isNotEqualTo(hojaRutaDTO2);
    }
}
