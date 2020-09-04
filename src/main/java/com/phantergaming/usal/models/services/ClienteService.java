package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ClienteService {

    Iterable<Cliente> getClientes();

    Iterable<Cliente> findAllByNombre(String nombre);
    
    Page<Cliente> getClientesPage(Pageable page);

    Cliente saveCliente(Cliente cliente);

    Optional<Cliente> getCliente(Long idCliente);

    void deleteCliente(Long idCliente);
}
