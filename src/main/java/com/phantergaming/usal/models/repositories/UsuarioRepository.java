package com.phantergaming.usal.models.repositories;

import com.phantergaming.usal.models.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findAllByOrderByIdDesc(Pageable pageable);

    Usuario findByUsername(String username);
}
