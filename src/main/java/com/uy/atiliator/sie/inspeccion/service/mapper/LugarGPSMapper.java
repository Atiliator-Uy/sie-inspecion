package com.uy.atiliator.sie.inspeccion.service.mapper;


import com.uy.atiliator.sie.inspeccion.domain.*;
import com.uy.atiliator.sie.inspeccion.service.dto.LugarGPSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LugarGPS} and its DTO {@link LugarGPSDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LugarGPSMapper extends EntityMapper<LugarGPSDTO, LugarGPS> {


    @Mapping(target = "hojarutas", ignore = true)
    @Mapping(target = "removeHojaruta", ignore = true)
    @Mapping(target = "inspecions", ignore = true)
    @Mapping(target = "removeInspecion", ignore = true)
    LugarGPS toEntity(LugarGPSDTO lugarGPSDTO);

    default LugarGPS fromId(Long id) {
        if (id == null) {
            return null;
        }
        LugarGPS lugarGPS = new LugarGPS();
        lugarGPS.setId(id);
        return lugarGPS;
    }
}
