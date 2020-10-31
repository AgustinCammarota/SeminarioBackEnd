package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Proveedor;
import com.phantergaming.usal.models.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    @Override
    public Iterable<Proveedor> getProveedores() {
        return repository.findAll();
    }

    @Override
    public Iterable<Proveedor> findAllByNombre(String termino) {
        return repository.findAllByNombre(termino);
    }

    @Override
    public Page<Proveedor> getProveedorPage(Pageable page) {
        return repository.findAllByOrderByIdDesc(page);
    }

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        return repository.save(proveedor);
    }

    @Override
    public Optional<Proveedor> getProveedor(Long idProveedor) {
        return repository.findById(idProveedor);
    }

    @Override
    public void deleteProveedor(Long idProveedor) {
        repository.deleteById(idProveedor);
    }

    @Override
    public void deleteProveedores(Iterable<Proveedor> proveedores) {
        repository.deleteAll(proveedores);
    }
}
