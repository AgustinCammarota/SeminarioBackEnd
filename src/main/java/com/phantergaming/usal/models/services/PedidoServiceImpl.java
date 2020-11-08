package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Pedido;
import com.phantergaming.usal.models.entities.Provincia;
import com.phantergaming.usal.models.repositories.PedidoRepository;
import com.phantergaming.usal.models.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Override
    public Iterable<Pedido> findAllByNombre(String nombre) {
        return repository.findAllByNombre(nombre);
    }

    @Override
    public Page<Pedido> getPedidosPage(Pageable page) {
        return repository.findAllByOrderByIdDesc(page);
    }

    @Override
    public Pedido savePedido(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public Iterable<Provincia> getAllProvincias() {
        return provinciaRepository.findAll();
    }

    @Override
    public void deletePedido(Long idPedido) {
        repository.deleteById(idPedido);
    }

}
