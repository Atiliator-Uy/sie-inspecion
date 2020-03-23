package com.uy.atiliator.sie.inspeccion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class EvidenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evidencia.class);
        Evidencia evidencia1 = new Evidencia();
        evidencia1.setId(1L);
        Evidencia evidencia2 = new Evidencia();
        evidencia2.setId(evidencia1.getId());
        assertThat(evidencia1).isEqualTo(evidencia2);
        evidencia2.setId(2L);
        assertThat(evidencia1).isNotEqualTo(evidencia2);
        evidencia1.setId(null);
        assertThat(evidencia1).isNotEqualTo(evidencia2);
    }
}
