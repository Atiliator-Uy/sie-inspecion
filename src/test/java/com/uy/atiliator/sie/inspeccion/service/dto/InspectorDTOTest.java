package com.uy.atiliator.sie.inspeccion.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.uy.atiliator.sie.inspeccion.web.rest.TestUtil;

public class InspectorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectorDTO.class);
        InspectorDTO inspectorDTO1 = new InspectorDTO();
        inspectorDTO1.setId(1L);
        InspectorDTO inspectorDTO2 = new InspectorDTO();
        assertThat(inspectorDTO1).isNotEqualTo(inspectorDTO2);
        inspectorDTO2.setId(inspectorDTO1.getId());
        assertThat(inspectorDTO1).isEqualTo(inspectorDTO2);
        inspectorDTO2.setId(2L);
        assertThat(inspectorDTO1).isNotEqualTo(inspectorDTO2);
        inspectorDTO1.setId(null);
        assertThat(inspectorDTO1).isNotEqualTo(inspectorDTO2);
    }
}
