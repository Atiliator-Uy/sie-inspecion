package com.uy.atiliator.sie.inspeccion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class EvidenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvidenciaDTO.class);
        EvidenciaDTO evidenciaDTO1 = new EvidenciaDTO();
        evidenciaDTO1.setId(1L);
        EvidenciaDTO evidenciaDTO2 = new EvidenciaDTO();
        assertThat(evidenciaDTO1).isNotEqualTo(evidenciaDTO2);
        evidenciaDTO2.setId(evidenciaDTO1.getId());
        assertThat(evidenciaDTO1).isEqualTo(evidenciaDTO2);
        evidenciaDTO2.setId(2L);
        assertThat(evidenciaDTO1).isNotEqualTo(evidenciaDTO2);
        evidenciaDTO1.setId(null);
        assertThat(evidenciaDTO1).isNotEqualTo(evidenciaDTO2);
    }
}
