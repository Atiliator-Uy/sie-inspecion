package com.uy.atiliator.sie.inspeccion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class InspeccionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspeccion.class);
        Inspeccion inspeccion1 = new Inspeccion();
        inspeccion1.setId(1L);
        Inspeccion inspeccion2 = new Inspeccion();
        inspeccion2.setId(inspeccion1.getId());
        assertThat(inspeccion1).isEqualTo(inspeccion2);
        inspeccion2.setId(2L);
        assertThat(inspeccion1).isNotEqualTo(inspeccion2);
        inspeccion1.setId(null);
        assertThat(inspeccion1).isNotEqualTo(inspeccion2);
    }
}
