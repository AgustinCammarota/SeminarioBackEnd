package com.phantergaming.usal.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "es requerido")
    private String nombre;

    @NotNull(message = "es requerido")
    @Column(unique = true)
    private String email;

    @NotNull(message = "es requerido")
    @Size(min = 7, max = 8, message = "no tiene un formato valido")
    @Column(unique = true)
    private String dni;

    @NotNull(message = "es requerido")
    private String telefono;

    @Column(name = "fecha_create")
    @Temporal(TemporalType.DATE)
    private Date fechaCreate;

    @PrePersist
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) &&
                Objects.equals(nombre, cliente.nombre) &&
                Objects.equals(email, cliente.email) &&
                Objects.equals(dni, cliente.dni) &&
                Objects.equals(telefono, cliente.telefono) &&
                Objects.equals(fechaCreate, cliente.fechaCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, email, dni, telefono, fechaCreate);
    }
}
