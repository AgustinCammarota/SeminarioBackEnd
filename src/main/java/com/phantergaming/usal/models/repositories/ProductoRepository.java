package com.phantergaming.usal.models.repositories;

import com.phantergaming.usal.models.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("select p from Producto p where p.nombre like %?1%")
    Iterable<Producto> findAllByNombre(String nombre);

    @Query("select p from Producto p where p.categoria.categoria = ?1")
    Iterable<Producto> findAllByCategoria(String categoria);

    Page<Producto> findAllByOrderByIdDesc(Pageable pageable);
}
