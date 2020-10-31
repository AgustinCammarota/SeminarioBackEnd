package com.phantergaming.usal.models.repositories;

import com.phantergaming.usal.models.entities.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query("select p from Proveedor p where p.nombre like %?1%")
    Iterable<Proveedor> findAllByNombre(String termino);

    Page<Proveedor> findAllByOrderByIdDesc(Pageable pageable);
}
