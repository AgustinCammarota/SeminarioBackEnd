package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Usuario findByUsername(String username);

    Page<Usuario> getUsuarioPage(Pageable page);

}
