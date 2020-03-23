package com.uy.atiliator.sie.inspeccion.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.uy.atiliator.sie.inspeccion.domain.Inspector} entity.
 */
public class InspectorDTO implements Serializable {
    
    private Long id;

    private String idUser;

    private String login;

    private String nombre;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

        InspectorDTO inspectorDTO = (InspectorDTO) o;
        if (inspectorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspectorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InspectorDTO{" +
            "id=" + getId() +
            ", idUser='" + getIdUser() + "'" +
            ", login='" + getLogin() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
