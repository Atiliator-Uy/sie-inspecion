package com.uy.atiliator.sie.inspeccion.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.uy.atiliator.sie.inspeccion.domain.enumeration.TipoInspeccion;

/**
 * A DTO for the {@link com.uy.atiliator.sie.inspeccion.domain.HojaRuta} entity.
 */
public class HojaRutaDTO implements Serializable {
    
    private Long id;

    private TipoInspeccion tipoInspeccion;

    private LocalDate fecha;

    private ZonedDateTime fechaHoraUTC;

    private String titulo;

    private String descripcion;

    private Boolean realizado;

    private Integer priodidad;


    private Long lugarGPSId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInspeccion getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(TipoInspeccion tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    public Integer getPriodidad() {
        return priodidad;
    }

    public void setPriodidad(Integer priodidad) {
        this.priodidad = priodidad;
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

        HojaRutaDTO hojaRutaDTO = (HojaRutaDTO) o;
        if (hojaRutaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hojaRutaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HojaRutaDTO{" +
            "id=" + getId() +
            ", tipoInspeccion='" + getTipoInspeccion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", fechaHoraUTC='" + getFechaHoraUTC() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", realizado='" + isRealizado() + "'" +
            ", priodidad=" + getPriodidad() +
            ", lugarGPSId=" + getLugarGPSId() +
            "}";
    }
}
