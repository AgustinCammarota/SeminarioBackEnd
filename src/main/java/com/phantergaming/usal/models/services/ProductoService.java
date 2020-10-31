package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Categoria;
import com.phantergaming.usal.models.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductoService {

    Iterable<Producto> getProductos();

    Iterable<Producto> findAllByNombre(String nombre);

    Iterable<Categoria> getCategorias();

    Page<Producto> getProductosPage(Pageable page);

    Producto saveProducto(Producto producto);

    Optional<Producto> getProducto(Long idProducto);

    void deleteProducto(Long idProducto);

    void deleteProductos(Iterable<Producto> productos);
}
