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

import com.uy.atiliator.sie.inspeccion.domain.enumeration.TipoInspeccion;

/**
 * A HojaRuta.
 */
@Entity
@Table(name = "hoja_ruta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "hojaruta")
public class HojaRuta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_inspeccion")
    private TipoInspeccion tipoInspeccion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "fecha_hora_utc")
    private ZonedDateTime fechaHoraUTC;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "realizado")
    private Boolean realizado;

    @Column(name = "priodidad")
    private Integer priodidad;

    @ManyToOne
    @JsonIgnoreProperties("hojarutas")
    private LugarGPS lugarGPS;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInspeccion getTipoInspeccion() {
        return tipoInspeccion;
    }

    public HojaRuta tipoInspeccion(TipoInspeccion tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
        return this;
    }

    public void setTipoInspeccion(TipoInspeccion tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public HojaRuta fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaHoraUTC() {
        return fechaHoraUTC;
    }

    public HojaRuta fechaHoraUTC(ZonedDateTime fechaHoraUTC) {
        this.fechaHoraUTC = fechaHoraUTC;
        return this;
    }

    public void setFechaHoraUTC(ZonedDateTime fechaHoraUTC) {
        this.fechaHoraUTC = fechaHoraUTC;
    }

    public String getTitulo() {
        return titulo;
    }

    public HojaRuta titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public HojaRuta descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isRealizado() {
        return realizado;
    }

    public HojaRuta realizado(Boolean realizado) {
        this.realizado = realizado;
        return this;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    public Integer getPriodidad() {
        return priodidad;
    }

    public HojaRuta priodidad(Integer priodidad) {
        this.priodidad = priodidad;
        return this;
    }

    public void setPriodidad(Integer priodidad) {
        this.priodidad = priodidad;
    }

    public LugarGPS getLugarGPS() {
        return lugarGPS;
    }

    public HojaRuta lugarGPS(LugarGPS lugarGPS) {
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
        if (!(o instanceof HojaRuta)) {
            return false;
        }
        return id != null && id.equals(((HojaRuta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HojaRuta{" +
            "id=" + getId() +
            ", tipoInspeccion='" + getTipoInspeccion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", fechaHoraUTC='" + getFechaHoraUTC() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", realizado='" + isRealizado() + "'" +
            ", priodidad=" + getPriodidad() +
            "}";
    }
}
