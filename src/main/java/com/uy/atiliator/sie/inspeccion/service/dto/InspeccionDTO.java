package com.uy.atiliator.sie.inspeccion.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.uy.atiliator.sie.inspeccion.domain.enumeration.TipoInspeccion;

/**
 * A DTO for the {@link com.uy.atiliator.sie.inspeccion.domain.Inspeccion} entity.
 */
public class InspeccionDTO implements Serializable {
    
    private Long id;

    private String observacion;

    private TipoInspeccion tipoInspeccion;

    private String recomendacion;

    private LocalDate fecha;

    private ZonedDateTime fechaHoraUTC;


    private Long inspectorId;

    private Long lugarGPSId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoInspeccion getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(TipoInspeccion tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaHoraUTC() {
        return fechaHoraUTC;
    }

    public void setFechaHoraUTC(ZonedDateTime fechaHoraUTC) {
        this.fechaHoraUTC = fechaHoraUTC;
    }

    public Long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Long getLugarGPSId() {
        return lugarGPSId;
    }

    public void setLugarGPSId(Long lugarGPSId) {
        this.lugarGPSId = lugarGPSId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InspeccionDTO inspeccionDTO = (InspeccionDTO) o;
        if (inspeccionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspeccionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InspeccionDTO{" +
            "id=" + getId() +
            ", observacion='" + getObservacion() + "'" +
            ", tipoInspeccion='" + getTipoInspeccion() + "'" +
            ", recomendacion='" + getRecomendacion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", fechaHoraUTC='" + getFechaHoraUTC() + "'" +
            ", inspectorId=" + getInspectorId() +
            ", lugarGPSId=" + getLugarGPSId() +
            "}";
    }
}
