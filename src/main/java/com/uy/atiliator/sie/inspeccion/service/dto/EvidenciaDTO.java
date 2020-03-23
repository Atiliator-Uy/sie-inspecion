package com.uy.atiliator.sie.inspeccion.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.uy.atiliator.sie.inspeccion.domain.Evidencia} entity.
 */
public class EvidenciaDTO implements Serializable {
    
    private Long id;

    private String nombre;

    private String descripcion;

    @Lob
    private byte[] foto;

    private String fotoContentType;
    private String url;


    private Long inspeccionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getInspeccionId() {
        return inspeccionId;
    }

    public void setInspeccionId(Long inspeccionId) {
        this.inspeccionId = inspeccionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvidenciaDTO evidenciaDTO = (EvidenciaDTO) o;
        if (evidenciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evidenciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvidenciaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", foto='" + getFoto() + "'" +
            ", url='" + getUrl() + "'" +
            ", inspeccionId=" + getInspeccionId() +
            "}";
    }
}
