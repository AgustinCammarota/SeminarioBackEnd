package com.phantergaming.usal.models.services;

import com.phantergaming.usal.models.entities.Usuario;
import com.phantergaming.usal.models.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> getUsuarioPage(Pageable page) {
        return usuarioRepository.findAllByOrderByIdDesc(page);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if(usuario == null) {
            logger.error("Error en el login: no existe el usuario en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario en el sistema!");
        }

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),
                true, true, true, authorities);
    }
}
