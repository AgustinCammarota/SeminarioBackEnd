package com.phantergaming.usal.controllers;

import com.phantergaming.usal.models.entities.Pedido;
import com.phantergaming.usal.models.services.PedidoService;
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

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    private final Map<String, Object> map = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @GetMapping("/page")
    public ResponseEntity<?> getPedidosPagina(Pageable page) {

        try {
            return ResponseEntity.ok().body(service.getPedidosPage(page));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar los Pedidos");
        }
    }

    @GetMapping("/direccion/provincias")
    public ResponseEntity<?> getProvincias() {

        try {
            return ResponseEntity.ok().body(service.getAllProvincias());
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar las Provincias");
        }
    }

    @GetMapping("/filtrar/{termino}")
    public ResponseEntity<?> getPedidosPorTermino(@PathVariable String termino) {

        try {
            return ResponseEntity.ok().body(service.findAllByNombre(termino));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar los Pedidos");
        }
    }

    @PostMapping()
    public ResponseEntity<?> savePedido(@Valid @RequestBody Pedido pedido, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.savePedido(pedido));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al guardar el Pedido");
        }
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long idPedido) {

        try {
            service.deletePedido(idPedido);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar el Pedido");
        }
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<?> validarCampos(BindingResult bindingResult) {
        bindingResult.getFieldErrors().forEach( error -> map.put("error", "El campo: " + error.getField() + " " + error.getDefaultMessage()));
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
