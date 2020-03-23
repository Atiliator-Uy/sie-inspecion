package com.uy.atiliator.sie.inspeccion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class HojaRutaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HojaRuta.class);
        HojaRuta hojaRuta1 = new HojaRuta();
        hojaRuta1.setId(1L);
        HojaRuta hojaRuta2 = new HojaRuta();
        hojaRuta2.setId(hojaRuta1.getId());
        assertThat(hojaRuta1).isEqualTo(hojaRuta2);
        hojaRuta2.setId(2L);
        assertThat(hojaRuta1).isNotEqualTo(hojaRuta2);
        hojaRuta1.setId(null);
        assertThat(hojaRuta1).isNotEqualTo(hojaRuta2);
    }
}
