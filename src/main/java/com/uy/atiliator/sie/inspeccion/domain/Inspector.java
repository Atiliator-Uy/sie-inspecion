package com.uy.atiliator.sie.inspeccion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Inspector.
 */
@Entity
@Table(name = "inspector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "inspector")
public class Inspector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private String idUser;

    @Column(name = "login")
    private String login;

    @Column(name = "nombre")
    private String nombre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public Inspector idUser(String idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public Inspector login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public Inspector nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspector)) {
            return false;
        }
        return id != null && id.equals(((Inspector) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inspector{" +
            "id=" + getId() +
            ", idUser='" + getIdUser() + "'" +
            ", login='" + getLogin() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
