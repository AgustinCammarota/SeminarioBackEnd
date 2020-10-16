package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Cliente;
import com.phantergaming.usal.models.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public Iterable<Cliente> getClientes() {
        return repository.findAll();
    }

    @Override
    public Iterable<Cliente> findAllByNombre(String nombre) {
        return repository.findAllByNombre(nombre);
    }

    @Override
    public Page<Cliente> getClientesPage(Pageable page) {
        return repository.findAllByOrderByIdDesc(page);
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Optional<Cliente> getCliente(Long idCliente) {
        return repository.findById(idCliente);
    }

    @Override
    public void deleteCliente(Long idCliente) {
        repository.deleteById(idCliente);
    }

    @Override
    public void deleteClientes(Iterable<Cliente> clientes) {
        repository.deleteAll(clientes);
    }
}
