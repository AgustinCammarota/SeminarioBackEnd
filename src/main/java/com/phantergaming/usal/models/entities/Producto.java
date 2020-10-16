package com.phantergaming.usal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "es requerido")
    private String nombre;

    @NotNull(message = "es requerido")
    private Double precio;

    @NotNull(message = "es requerido")
    private Long cantidad;

    private String descripcion;

    private Boolean estado;

    @Lob
    @JsonIgnore
    private byte[] archivo;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;

    @PrePersist
    private void prePersistFecha() {
        this.fechaCreate = new Date();
    }

    public  Integer getarchivoHashCode() {
        return (this.archivo != null) ? this.archivo.hashCode(): null;
    }

    public byte[] getFoto() {
        return archivo;
    }

    public void setFoto(byte[] archivo) {
        this.archivo = archivo;
    }

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getFechaCreate() {
        return fechaCreate;
    }

    public void setFechaCreate(Date fechaCreate) {
        this.fechaCreate = fechaCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id) &&
                Objects.equals(nombre, producto.nombre) &&
                Objects.equals(precio, producto.precio) &&
                Objects.equals(cantidad, producto.cantidad) &&
                Objects.equals(descripcion, producto.descripcion) &&
                Objects.equals(estado, producto.estado) &&
                Arrays.equals(archivo, producto.archivo) &&
                Objects.equals(fechaCreate, producto.fechaCreate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nombre, precio, cantidad, descripcion, estado, fechaCreate);
        result = 31 * result + Arrays.hashCode(archivo);
        return result;
    }
}
