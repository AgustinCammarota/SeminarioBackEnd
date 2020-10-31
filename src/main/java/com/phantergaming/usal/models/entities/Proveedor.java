package com.phantergaming.usal.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "proveedores")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    private String direccion;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String telefono;

    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private List<Producto> producto;

    @PrePersist()
    private void prePersistFecha() {
        this.fechaCreate = new Date();
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaCreate() {
        return fechaCreate;
    }

    public void setFechaCreate(Date fechaCreate) {
        this.fechaCreate = fechaCreate;
    }

    public List<Producto> getProducto() {
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proveedor)) return false;
        Proveedor proveedor = (Proveedor) o;
        return Objects.equals(id, proveedor.id) &&
                Objects.equals(nombre, proveedor.nombre) &&
                Objects.equals(direccion, proveedor.direccion) &&
                Objects.equals(email, proveedor.email) &&
                Objects.equals(telefono, proveedor.telefono) &&
                Objects.equals(fechaCreate, proveedor.fechaCreate) &&
                Objects.equals(producto, proveedor.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, direccion, email, telefono, fechaCreate, producto);
    }
}
