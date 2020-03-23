package com.uy.atiliator.sie.inspeccion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class InspeccionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspeccionDTO.class);
        InspeccionDTO inspeccionDTO1 = new InspeccionDTO();
        inspeccionDTO1.setId(1L);
        InspeccionDTO inspeccionDTO2 = new InspeccionDTO();
        assertThat(inspeccionDTO1).isNotEqualTo(inspeccionDTO2);
        inspeccionDTO2.setId(inspeccionDTO1.getId());
        assertThat(inspeccionDTO1).isEqualTo(inspeccionDTO2);
        inspeccionDTO2.setId(2L);
        assertThat(inspeccionDTO1).isNotEqualTo(inspeccionDTO2);
        inspeccionDTO1.setId(null);
        assertThat(inspeccionDTO1).isNotEqualTo(inspeccionDTO2);
    }
}
