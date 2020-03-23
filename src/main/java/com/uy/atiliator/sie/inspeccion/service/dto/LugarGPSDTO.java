package com.uy.atiliator.sie.inspeccion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.uy.atiliator.sie.inspeccion.domain.LugarGPS} entity.
 */
public class LugarGPSDTO implements Serializable {
    
    private Long id;

    private String altitud;

    private String latitud;

    private String longitud;

    private String nombre;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LugarGPSDTO lugarGPSDTO = (LugarGPSDTO) o;
        if (lugarGPSDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lugarGPSDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LugarGPSDTO{" +
            "id=" + getId() +
            ", altitud='" + getAltitud() + "'" +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
