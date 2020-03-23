package com.uy.atiliator.sie.inspeccion.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class InspectorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspector.class);
        Inspector inspector1 = new Inspector();
        inspector1.setId(1L);
        Inspector inspector2 = new Inspector();
        inspector2.setId(inspector1.getId());
        assertThat(inspector1).isEqualTo(inspector2);
        inspector2.setId(2L);
        assertThat(inspector1).isNotEqualTo(inspector2);
        inspector1.setId(null);
        assertThat(inspector1).isNotEqualTo(inspector2);
    }
}
