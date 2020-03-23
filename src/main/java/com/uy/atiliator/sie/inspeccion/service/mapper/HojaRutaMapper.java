package com.uy.atiliator.sie.inspeccion.service.mapper;


import com.uy.atiliator.sie.inspeccion.domain.*;
import com.uy.atiliator.sie.inspeccion.service.dto.HojaRutaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HojaRuta} and its DTO {@link HojaRutaDTO}.
 */
@Mapper(componentModel = "spring", uses = {LugarGPSMapper.class})
public interface HojaRutaMapper extends EntityMapper<HojaRutaDTO, HojaRuta> {

    @Mapping(source = "lugarGPS.id", target = "lugarGPSId")
    HojaRutaDTO toDto(HojaRuta hojaRuta);

    @Mapping(source = "lugarGPSId", target = "lugarGPS")
    HojaRuta toEntity(HojaRutaDTO hojaRutaDTO);

    default HojaRuta fromId(Long id) {
        if (id == null) {
            return null;
        }
        HojaRuta hojaRuta = new HojaRuta();
        hojaRuta.setId(id);
        return hojaRuta;
    }
}
