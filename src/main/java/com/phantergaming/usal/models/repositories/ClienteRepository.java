package com.phantergaming.usal.models.repositories;


import com.phantergaming.usal.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c where c.nombre like %?1%")
    Iterable<Cliente> findAllByNombre(String nombre);
}
