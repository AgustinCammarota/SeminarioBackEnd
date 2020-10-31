package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProveedorService {

    Iterable<Proveedor> getProveedores();

    Iterable<Proveedor> findAllByNombre(String termino);

    Page<Proveedor> getProveedorPage(Pageable page);

    Proveedor saveProveedor(Proveedor proveedor);

    Optional<Proveedor> getProveedor(Long idProveedor);

    void deleteProveedor(Long idProveedor);

    void deleteProveedores(Iterable<Proveedor> proveedores);
}
