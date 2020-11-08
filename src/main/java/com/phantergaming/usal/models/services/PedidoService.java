package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Pedido;
import com.phantergaming.usal.models.entities.Provincia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PedidoService {

    Iterable<Pedido> findAllByNombre(String nombre);

    Page<Pedido> getPedidosPage(Pageable page);

    Pedido savePedido(Pedido pedido);

    Iterable<Provincia> getAllProvincias();

    void deletePedido(Long idPedido);

}
