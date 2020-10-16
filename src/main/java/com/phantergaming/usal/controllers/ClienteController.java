package com.phantergaming.usal.controllers;

import com.phantergaming.usal.models.entities.Cliente;
import com.phantergaming.usal.models.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    private final Map<String, Object> map = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    Cliente clienteActual = new Cliente();

    Optional<Cliente> clienteOptional = Optional.empty();



    @GetMapping("/page")
    public ResponseEntity<?> getClientesPagina(Pageable page) {

        try {
            return ResponseEntity.ok().body(service.getClientesPage(page));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Clientes");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getClientes() {

        try {
            return ResponseEntity.ok().body(service.getClientes());
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Clientes");
        }
    }

    @GetMapping("/filtrar/{nombre}")
    public ResponseEntity<?> getClientesPorNombre(@PathVariable String nombre) {

        try {
            return ResponseEntity.ok().body(service.findAllByNombre(nombre));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Clientes");
        }
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> getCliente(@PathVariable Long idCliente) {

        try {
            clienteOptional = service.getCliente(idCliente);
        } catch (DataAccessException e) {
           return this.devolverError(e, "Error al buscar al Cliente");
        }

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(clienteOptional.get());
    }

    @PostMapping()
    public ResponseEntity<?> saveCliente(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveCliente(cliente));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al guardar al Cliente");
        }
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, BindingResult bindingResult, @PathVariable Long idCliente) {

        try {
            clienteOptional = service.getCliente(idCliente);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar al Cliente");
        }

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        clienteActual = clienteOptional.get();
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setDni(cliente.getDni());
        clienteActual.setEmail(cliente.getEmail());
        clienteActual.setTelefono(cliente.getTelefono());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveCliente(clienteActual));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al actualizar al Cliente");
        }
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long idCliente) {

        try {
            service.deleteCliente(idCliente);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar al Cliente");
        }
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> validarCampos(BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach( error -> map.put("error", "El campo: " + error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(map);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteProductos(@RequestBody Iterable<Cliente> clientes) {

        try {
            service.deleteClientes(clientes);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar los Clientes");
        }
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> devolverError(DataAccessException e, String mensaje) {
        String mensajeError = e.getMostSpecificCause().getMessage();
        map.put("mensaje", mensaje);
        map.put("error", mensajeError);
        logger.error(mensajeError);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

}
