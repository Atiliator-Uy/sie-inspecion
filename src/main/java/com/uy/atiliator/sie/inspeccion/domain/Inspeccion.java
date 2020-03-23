package com.uy.atiliator.sie.inspeccion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.uy.atiliator.sie.inspeccion.domain.enumeration.TipoInspeccion;

/**
 * A Inspeccion.
 */
@Entity
@Table(name = "inspeccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "inspeccion")
public class Inspeccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "observacion")
    private String observacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_inspeccion")
    private TipoInspeccion tipoInspeccion;

    @Column(name = "recomendacion")
    private String recomendacion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "fecha_hora_utc")
    private ZonedDateTime fechaHoraUTC;

    @OneToMany(mappedBy = "inspeccion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evidencia> evidencias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("inspeccions")
    private Inspector inspector;

    @ManyToOne
    @JsonIgnoreProperties("inspecions")
    private LugarGPS lugarGPS;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacion() {
        return observacion;
    }

    public Inspeccion observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoInspeccion getTipoInspeccion() {
        return tipoInspeccion;
    }

    public Inspeccion tipoInspeccion(TipoInspeccion tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
        return this;
    }

    public void setTipoInspeccion(TipoInspeccion tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public Inspeccion recomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
        return this;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Inspeccion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaHoraUTC() {
        return fechaHoraUTC;
    }

    public Inspeccion fechaHoraUTC(ZonedDateTime fechaHoraUTC) {
        this.fechaHoraUTC = fechaHoraUTC;
        return this;
    }

    public void setFechaHoraUTC(ZonedDateTime fechaHoraUTC) {
        this.fechaHoraUTC = fechaHoraUTC;
    }

    public Set<Evidencia> getEvidencias() {
        return evidencias;
    }

    public Inspeccion evidencias(Set<Evidencia> evidencias) {
        this.evidencias = evidencias;
        return this;
    }

    public Inspeccion addEvidencia(Evidencia evidencia) {
        this.evidencias.add(evidencia);
        evidencia.setInspeccion(this);
        return this;
    }

    public Inspeccion removeEvidencia(Evidencia evidencia) {
        this.evidencias.remove(evidencia);
        evidencia.setInspeccion(null);
        return this;
    }

    public void setEvidencias(Set<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    public Inspector getInspector() {
        return inspector;
    }

    public Inspeccion inspector(Inspector inspector) {
        this.inspector = inspector;
        return this;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

    public LugarGPS getLugarGPS() {
        return lugarGPS;
    }

    public Inspeccion lugarGPS(LugarGPS lugarGPS) {
        this.lugarGPS = lugarGPS;
        return this;
    }

    public void setLugarGPS(LugarGPS lugarGPS) {
        this.lugarGPS = lugarGPS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspeccion)) {
            return false;
        }
        return id != null && id.equals(((Inspeccion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inspeccion{" +
            "id=" + getId() +
            ", observacion='" + getObservacion() + "'" +
            ", tipoInspeccion='" + getTipoInspeccion() + "'" +
            ", recomendacion='" + getRecomendacion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", fechaHoraUTC='" + getFechaHoraUTC() + "'" +
            "}";
    }
}
