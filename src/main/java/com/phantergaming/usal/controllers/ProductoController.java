package com.phantergaming.usal.controllers;

import com.phantergaming.usal.models.entities.Producto;
import com.phantergaming.usal.models.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    private final Map<String, Object> map = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    Producto productoActual = new Producto();

    Optional<Producto> productoOptional = Optional.empty();


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/page")
    public ResponseEntity<?> getProductosPagina(Pageable page) {

        try {
            return ResponseEntity.ok().body(service.getProductosPage(page));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Productos");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<?> getProductos() {

        try {
            return ResponseEntity.ok().body(service.getProductos());
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Productos");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/categorias")
    public ResponseEntity<?> getCategorias() {

        try {
            return ResponseEntity.ok().body(service.getCategorias());
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a las Categorias");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/filtrar/{nombre}")
    public ResponseEntity<?> getProductosPorNombre(@PathVariable String nombre) {

        try {
            return ResponseEntity.ok().body(service.findAllByNombre(nombre));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar a los Productos");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{idProducto}")
    public ResponseEntity<?> getProducto(@PathVariable Long idProducto) {

        try {
            productoOptional = service.getProducto(idProducto);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar al Producto");
        }

        if (!productoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(productoOptional.get());
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping()
    public ResponseEntity<?> saveProducto(@Valid @RequestBody Producto producto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveProducto(producto));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al guardar al Producto");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,
                                    @RequestParam("idProducto") Long idProducto,
                                    @RequestParam("nombreFoto") String nombreFoto) {
        try {
            productoOptional = service.getProducto(idProducto);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar al Producto");
        }

        if (!productoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            if (!archivo.isEmpty()) {
                productoActual = productoOptional.get();
                productoActual.setFoto(archivo.getBytes());
                productoActual.setNombreFoto(nombreFoto);
            }
        } catch (IOException e) {
            map.put("mensaje", "Error al subir la imagen del producto");
            map.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveProducto(productoActual));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al guardar al Producto");
        }
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{idProducto}")
    public ResponseEntity<?> updateProducto(@Valid @RequestBody Producto producto, BindingResult bindingResult, @PathVariable Long idProducto) {

        try {
            productoOptional = service.getProducto(idProducto);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al buscar al Producto");
        }

        if (!productoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (bindingResult.hasErrors()) {
            return validarCampos(bindingResult);
        }

        productoActual = productoOptional.get();
        productoActual.setNombre(producto.getNombre());
        productoActual.setCantidad(producto.getCantidad());
        productoActual.setCategoria(producto.getCategoria());
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setFechaCreate(producto.getFechaCreate());
        productoActual.setNombreFoto(producto.getNombreFoto());
        productoActual.setPrecio(producto.getPrecio());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveProducto(productoActual));
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al actualizar al Producto");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/uploads/img/{idProducto}")
    public ResponseEntity<?> verFoto(@PathVariable Long idProducto) {
        productoOptional = service.getProducto(idProducto);

        if (!productoOptional.isPresent() || productoOptional.get().getFoto() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource imagen = new ByteArrayResource(productoOptional.get().getFoto());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long idProducto) {

        try {
            service.deleteProducto(idProducto);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar al Producto");
        }
        return ResponseEntity.noContent().build();
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping()
    public ResponseEntity<?> deleteProductos(@RequestBody Iterable<Producto> productos) {

        try {
            service.deleteProductos(productos);
        } catch (DataAccessException e) {
            return this.devolverError(e, "Error al eliminar los Productos");
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
