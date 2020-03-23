package com.uy.atiliator.sie.inspeccion.service.mapper;


import com.uy.atiliator.sie.inspeccion.domain.*;
import com.uy.atiliator.sie.inspeccion.service.dto.InspeccionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inspeccion} and its DTO {@link InspeccionDTO}.
 */
@Mapper(componentModel = "spring", uses = {InspectorMapper.class, LugarGPSMapper.class})
public interface InspeccionMapper extends EntityMapper<InspeccionDTO, Inspeccion> {

    @Mapping(source = "inspector.id", target = "inspectorId")
    @Mapping(source = "lugarGPS.id", target = "lugarGPSId")
    InspeccionDTO toDto(Inspeccion inspeccion);

    @Mapping(target = "evidencias", ignore = true)
    @Mapping(target = "removeEvidencia", ignore = true)
    @Mapping(source = "inspectorId", target = "inspector")
    @Mapping(source = "lugarGPSId", target = "lugarGPS")
    Inspeccion toEntity(InspeccionDTO inspeccionDTO);

    default Inspeccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(id);
        return inspeccion;
    }
}
