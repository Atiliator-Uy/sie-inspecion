package com.uy.atiliator.sie.inspeccion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class LugarGPSTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LugarGPS.class);
        LugarGPS lugarGPS1 = new LugarGPS();
        lugarGPS1.setId(1L);
        LugarGPS lugarGPS2 = new LugarGPS();
        lugarGPS2.setId(lugarGPS1.getId());
        assertThat(lugarGPS1).isEqualTo(lugarGPS2);
        lugarGPS2.setId(2L);
        assertThat(lugarGPS1).isNotEqualTo(lugarGPS2);
        lugarGPS1.setId(null);
        assertThat(lugarGPS1).isNotEqualTo(lugarGPS2);
    }
}
