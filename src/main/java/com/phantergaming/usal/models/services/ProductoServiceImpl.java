package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Categoria;
import com.phantergaming.usal.models.entities.Producto;
import com.phantergaming.usal.models.repositories.CategoriaRepository;
import com.phantergaming.usal.models.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Iterable<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Iterable<Producto> findAllByNombre(String nombre) {
        return productoRepository.findAllByNombre(nombre);
    }

    @Override
    public Iterable<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Page<Producto> getProductosPage(Pageable page) {
        return productoRepository.findAllByOrderByIdDesc(page);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getProducto(Long idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Override
    public void deleteProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

    @Override
    public void deleteProductos(Iterable<Producto> productos) {
      productoRepository.deleteAll(productos);
    }
}
