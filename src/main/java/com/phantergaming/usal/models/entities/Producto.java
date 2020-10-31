package com.phantergaming.usal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "es requerido")
    private String nombre;

    @NotEmpty(message = "es requerido")
    private String precio;

    @NotNull(message = "es requerido")
    private Long cantidad;

    @NotEmpty(message = "es requerido")
    private String descripcion;

    @Column(name = "nombre_foto")
    private String nombreFoto;

    @Lob
    @JsonIgnore
    private byte[] foto;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;


    public  Integer getarchivoHashCode() {
        return (this.foto != null) ? this.foto.hashCode(): null;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
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

    public String getPrecio() {
        return precio;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public void setPrecio(String precio) {
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
                Objects.equals(nombreFoto, producto.nombreFoto) &&
                Arrays.equals(foto, producto.foto) &&
                Objects.equals(categoria, producto.categoria) &&
                Objects.equals(fechaCreate, producto.fechaCreate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nombre, precio, cantidad, descripcion, nombreFoto, categoria, fechaCreate);
        result = 31 * result + Arrays.hashCode(foto);
        return result;
    }
}
