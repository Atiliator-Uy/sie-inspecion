package com.uy.atiliator.sie.inspeccion.service.mapper;


import com.uy.atiliator.sie.inspeccion.domain.*;
import com.uy.atiliator.sie.inspeccion.service.dto.InspectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inspector} and its DTO {@link InspectorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InspectorMapper extends EntityMapper<InspectorDTO, Inspector> {



    default Inspector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inspector inspector = new Inspector();
        inspector.setId(id);
        return inspector;
    }
}
