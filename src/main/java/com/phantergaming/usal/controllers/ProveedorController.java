package com.phantergaming.usal.controllers;

import com.phantergaming.usal.models.entities.Proveedor;
import com.phantergaming.usal.models.services.ProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    private final Map<String, Object> map = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    Proveedor proveedorActual = new Proveedor();

    Optional<Proveedor> proveedorOptional = Optional.empty();


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/page")
    public ResponseEntity<?> getProveedoresPagina(Pageable page) {

        try {
            return ResponseEntity.ok().body(service.getProveedorPage(page));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Proveedores");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<?> getProveedores() {

        try {
            return ResponseEntity.ok().body(service.getProveedores());
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Proveedores");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/filtrar/{termino}")
    public ResponseEntity<?> getProveedoresPorNombre(@PathVariable String termino) {

        try {
            return ResponseEntity.ok().body(service.findAllByNombre(termino));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Proveedores");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{idProveedor}")
    public ResponseEntity<?> getProveedor(@PathVariable Long idProveedor) {

        try {
            proveedorOptional = service.getProveedor(idProveedor);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar al Proveedor");
        }

        if (!proveedorOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(proveedorOptional.get());
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping()
    public ResponseEntity<?> saveProveedor(@Valid @RequestBody Proveedor proveedor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveProveedor(proveedor));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al guardar al Proveedor");
        }
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{idProveedor}")
    public ResponseEntity<?> updateProveedor(@Valid @RequestBody Proveedor proveedor, BindingResult bindingResult, @PathVariable Long idProveedor) {

        try {
            proveedorOptional = service.getProveedor(idProveedor);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar al Proveedor");
        }

        if (!proveedorOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        proveedorActual = proveedorOptional.get();
        proveedorActual.setNombre(proveedor.getNombre());
        proveedorActual.setDireccion(proveedor.getDireccion());
        proveedorActual.setEmail(proveedor.getEmail());
        proveedorActual.setTelefono(proveedor.getTelefono());
        proveedorActual.setProductos(proveedor.getProductos());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveProveedor(proveedorActual));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al actualizar al Proveedor");
        }
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{idProveedor}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long idProveedor) {

        try {
            service.deleteProveedor(idProveedor);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar al Proveedor");
        }
        return ResponseEntity.noContent().build();
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping()
    public ResponseEntity<?> deleteProveedor(@RequestBody Iterable<Proveedor> proveedores) {

        try {
            service.deleteProveedores(proveedores);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar los Proveedores");
        }
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> validarCampos(BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach(error -> map.put("error", "El campo: " + error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(map);
    }

    private ResponseEntity<?> devolverError(DataAccessException e, String mensaje) {
        String mensajeError = e.getMostSpecificCause().getMessage();
        map.put("mensaje", mensaje);
        map.put("error", mensajeError);
        logger.error(mensajeError);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
}
