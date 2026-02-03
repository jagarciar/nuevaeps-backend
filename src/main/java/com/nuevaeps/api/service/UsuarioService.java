package com.nuevaeps.api.service;

import com.nuevaeps.api.model.Rol;
import com.nuevaeps.api.model.Usuario;
import com.nuevaeps.api.repository.RolRepository;
import com.nuevaeps.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(String username, String password) {
        Usuario usuario = new Usuario(username, passwordEncoder.encode(password));
        
        // Asignar rol USER por defecto
        Rol rolUser = rolRepository.findByNombre("USER")
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol("USER");
                    return rolRepository.save(nuevoRol);
                });
        
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);
        
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
