package com.phantergaming.usal.models.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

    public Categoria(Long id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public Categoria() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria1 = (Categoria) o;
        return Objects.equals(id, categoria1.id) &&
                Objects.equals(categoria, categoria1.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria);
    }
}
