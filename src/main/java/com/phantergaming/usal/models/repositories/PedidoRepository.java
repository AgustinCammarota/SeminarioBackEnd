package com.phantergaming.usal.models.repositories;

import com.phantergaming.usal.models.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("select p from Pedido p where p.cliente.nombre like %?1%")
    Iterable<Pedido> findAllByNombre(String nombre);

    Page<Pedido> findAllByOrderByIdDesc(Pageable pageable);
}
