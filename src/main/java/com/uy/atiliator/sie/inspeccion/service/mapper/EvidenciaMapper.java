package com.uy.atiliator.sie.inspeccion.service.mapper;


import com.uy.atiliator.sie.inspeccion.domain.*;
import com.uy.atiliator.sie.inspeccion.service.dto.EvidenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evidencia} and its DTO {@link EvidenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {InspeccionMapper.class})
public interface EvidenciaMapper extends EntityMapper<EvidenciaDTO, Evidencia> {

    @Mapping(source = "inspeccion.id", target = "inspeccionId")
    EvidenciaDTO toDto(Evidencia evidencia);

    @Mapping(source = "inspeccionId", target = "inspeccion")
    Evidencia toEntity(EvidenciaDTO evidenciaDTO);

    default Evidencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Evidencia evidencia = new Evidencia();
        evidencia.setId(id);
        return evidencia;
    }
}
