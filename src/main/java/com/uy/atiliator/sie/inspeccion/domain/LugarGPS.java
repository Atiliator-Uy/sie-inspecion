package com.uy.atiliator.sie.inspeccion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A LugarGPS.
 */
@Entity
@Table(name = "lugar_gps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lugargps")
public class LugarGPS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "altitud")
    private String altitud;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "lugarGPS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HojaRuta> hojarutas = new HashSet<>();

    @OneToMany(mappedBy = "lugarGPS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inspeccion> inspecions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAltitud() {
        return altitud;
    }

    public LugarGPS altitud(String altitud) {
        this.altitud = altitud;
        return this;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public LugarGPS latitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public LugarGPS longitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public LugarGPS nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<HojaRuta> getHojarutas() {
        return hojarutas;
    }

    public LugarGPS hojarutas(Set<HojaRuta> hojaRutas) {
        this.hojarutas = hojaRutas;
        return this;
    }

    public LugarGPS addHojaruta(HojaRuta hojaRuta) {
        this.hojarutas.add(hojaRuta);
        hojaRuta.setLugarGPS(this);
        return this;
    }

    public LugarGPS removeHojaruta(HojaRuta hojaRuta) {
        this.hojarutas.remove(hojaRuta);
        hojaRuta.setLugarGPS(null);
        return this;
    }

    public void setHojarutas(Set<HojaRuta> hojaRutas) {
        this.hojarutas = hojaRutas;
    }

    public Set<Inspeccion> getInspecions() {
        return inspecions;
    }

    public LugarGPS inspecions(Set<Inspeccion> inspeccions) {
        this.inspecions = inspeccions;
        return this;
    }

    public LugarGPS addInspecion(Inspeccion inspeccion) {
        this.inspecions.add(inspeccion);
        inspeccion.setLugarGPS(this);
        return this;
    }

    public LugarGPS removeInspecion(Inspeccion inspeccion) {
        this.inspecions.remove(inspeccion);
        inspeccion.setLugarGPS(null);
        return this;
    }

    public void setInspecions(Set<Inspeccion> inspeccions) {
        this.inspecions = inspeccions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LugarGPS)) {
            return false;
        }
        return id != null && id.equals(((LugarGPS) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LugarGPS{" +
            "id=" + getId() +
            ", altitud='" + getAltitud() + "'" +
            ", latitud='" + getLatitud() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
